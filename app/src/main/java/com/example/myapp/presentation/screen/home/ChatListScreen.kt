package com.example.myapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapp.R
import com.example.myapp.domain.model.Chat
import com.example.myapp.presentation.screen.theme.AppColors
import com.example.myapp.presentation.screen.theme.Dimens
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ChatListScreen(
    onChatClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is ChatListSideEffect.NavigateToChat -> onChatClick(effect.chatId)
            }
        }
    }

    ChatListScreenContent(
        uiState = uiState,
        onEvent = { event ->
            when (event) {
                ChatListEvent.OnSearchClick -> {
                    viewModel.onEvent(event)
                    onSearchClick()
                }
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
private fun ChatListScreenContent(
    uiState: ChatListState,
    onEvent: (ChatListEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.onBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.spaceLg, vertical = Dimens.spaceLg)
        ) {
            if (uiState.loader) {
                Loader()
            }

            Spacer(Modifier.height(Dimens.spaceLg))

            TopBar(
                query = uiState.queryInput,
                onQueryChanged = { onEvent(ChatListEvent.OnQueryChanged(it)) },
                onSearchClick = { onEvent(ChatListEvent.OnSearchClick) }
            )

            Spacer(Modifier.height(Dimens.spaceLg))

            if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = AppColors.canceled,
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(Dimens.spaceMd))
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dimens.spaceSm),
                contentPadding = PaddingValues(bottom = Dimens.spaceXxl)
            ) {
                items(
                    count = uiState.filteredChats.size,
                    key = { uiState.filteredChats[it].id }
                ) { index ->
                    val chat = uiState.filteredChats[index]
                    ChatRow(
                        chat = chat,
                        onClick = { onEvent(ChatListEvent.OnChatClick(chat.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(Dimens.spaceMd))

        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(Dimens.radiusMd))
                .background(AppColors.secondary)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = null,
                tint = AppColors.onPrimary,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(Dimens.radiusMd))
            .background(AppColors.surface)
            .padding(horizontal = Dimens.spaceMd),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.google),
            contentDescription = null,
            tint = AppColors.onSurface,
            modifier = Modifier.size(18.dp)
        )

        Spacer(Modifier.width(Dimens.spaceSm))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(AppColors.primary),
            textStyle = TextStyle(
                color = AppColors.onBackground,
                fontSize = 14.sp,
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                if (value.isBlank()) {
                    Text(
                        text = "Search",
                        color = AppColors.onSurface,
                        fontSize = 14.sp,
                    )
                }
                inner()
            }
        )
    }
}

@Composable
private fun ChatRow(
    chat: Chat,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = AppColors.surface,
        shape = RoundedCornerShape(Dimens.radiusMd)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.spaceMd, vertical = Dimens.spaceMd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(imageUrl = chat.image, isTyping = chat.isTyping)

            Spacer(Modifier.width(Dimens.spaceMd))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chat.owner,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = AppColors.onBackground
                )

                Spacer(Modifier.height(2.dp))

                val subtitle = when (chat.lastMessageType.lowercase()) {
                    "voice" -> "Sent a voice message"
                    "attachment" -> "Sent an attachment"
                    else -> chat.lastMessage
                }

                Text(
                    text = subtitle,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = AppColors.onSurface,
                    maxLines = 1
                )
            }

            Spacer(Modifier.width(Dimens.spaceSm))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = chat.lastActive,
                    fontSize = 12.sp,
                    color = AppColors.onSurface
                )

                Spacer(Modifier.height(6.dp))

                if (chat.unreadMessages > 0) {
                    UnreadBadge(chat.unreadMessages)
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Dot()
                        Dot()
                        Dot()
                    }
                }
            }
        }
    }
}

@Composable
private fun Avatar(
    imageUrl: String?,
    isTyping: Boolean
) {
    val model = if (imageUrl.isNullOrBlank()) {
        R.drawable.ic_default_avatar
    } else {
        ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncImage(
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AppColors.divider),
            error = painterResource(R.drawable.ic_default_avatar),
            placeholder = painterResource(R.drawable.ic_default_avatar)
        )

        if (isTyping) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(AppColors.pending)
            )
        }
    }
}


@Composable
private fun UnreadBadge(count: Int) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(AppColors.secondary)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.onBackground
        )
    }
}

@Composable
private fun Dot() {
    Box(
        modifier = Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(AppColors.onSurface)
    )
}

@Composable
private fun Loader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = AppColors.primary)
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListScreenPreview() {
    ChatListScreenContent(
        uiState = ChatListState(
            loader = false,
            queryInput = "",
            queryApplied = "",
            chats = listOf(
                Chat(
                    id = 1,
                    image = "",
                    owner = "Alice Smith",
                    lastMessage = "Great. I will have a look",
                    lastActive = "4:20 PM",
                    unreadMessages = 3,
                    isTyping = false,
                    lastMessageType = "text"
                ),
                Chat(
                    id = 2,
                    image = "",
                    owner = "Alice Smith",
                    lastMessage = "",
                    lastActive = "4:20 PM",
                    unreadMessages = 0,
                    isTyping = true,
                    lastMessageType = "voice"
                ),
                Chat(
                    id = 3,
                    image = "",
                    owner = "Alice Smith",
                    lastMessage = "",
                    lastActive = "4:20 PM",
                    unreadMessages = 1,
                    isTyping = false,
                    lastMessageType = "attachment"
                )
            ),
            filteredChats = listOf(
                Chat(
                    id = 1,
                    image = "",
                    owner = "Alice Smith",
                    lastMessage = "Great. I will have a look",
                    lastActive = "4:20 PM",
                    unreadMessages = 3,
                    isTyping = false,
                    lastMessageType = "text"
                ),
                Chat(
                    id = 2,
                    image = "",
                    owner = "Alice Smith",
                    lastMessage = "",
                    lastActive = "4:20 PM",
                    unreadMessages = 0,
                    isTyping = true,
                    lastMessageType = "voice"
                ),
                Chat(
                    id = 3,
                    image = "",
                    owner = "Alice Smith",
                    lastMessage = "",
                    lastActive = "4:20 PM",
                    unreadMessages = 1,
                    isTyping = false,
                    lastMessageType = "attachment"
                )
            ),
            error = null
        ),
        onEvent = {}
    )
}


