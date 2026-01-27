package com.example.myapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
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
import com.example.myapp.presentation.common.UiRes.Drawable.GOOGLE
import com.example.myapp.presentation.common.UiRes.Drawable.IC_DEFAULT_AVATAR
import com.example.myapp.presentation.common.UiRes.Drawable.IC_FILTER
import com.example.myapp.presentation.common.UiRes.String.ATTACHMENT
import com.example.myapp.presentation.common.UiRes.String.SEARCH
import com.example.myapp.presentation.common.UiRes.String.SENT_AN_ATTACHMENT
import com.example.myapp.presentation.common.UiRes.String.SENT_A_VOICE_MESSAGE
import com.example.myapp.presentation.common.UiRes.String.VOICE
import com.example.myapp.presentation.screen.theme.AppColors
import com.example.myapp.presentation.screen.theme.FontSize
import com.example.myapp.presentation.screen.theme.Height
import com.example.myapp.presentation.screen.theme.IconSize
import com.example.myapp.presentation.screen.theme.Padding
import com.example.myapp.presentation.screen.theme.Radius
import com.example.myapp.presentation.screen.theme.SpaceBy
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
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SpaceBy.spaceBy24)
        ) {
            if (uiState.loader) {
                Loader()
            }

            Spacer(Modifier.height(Height.height24))

            TopBar(
                query = uiState.queryInput,
                onQueryChanged = { onEvent(ChatListEvent.OnQueryChanged(it)) },
                onSearchClick = { onEvent(ChatListEvent.OnSearchClick) }
            )

            Spacer(Modifier.height(Height.height24))

            if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = AppColors.canceled,
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(Height.height20))
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(SpaceBy.spaceBy18),
                contentPadding = PaddingValues(bottom = Padding.padding32)
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

        Spacer(Modifier.width(SpaceBy.spaceBy20))

        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(52.dp)
                .clip(Radius.radius12)
                .background(AppColors.secondary)
        ) {
            Icon(
                painter = painterResource(IC_FILTER),
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
            .clip(Radius.radius12)
            .background(AppColors.surface)
            .padding(horizontal = Padding.padding20),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(GOOGLE),
            contentDescription = null,
            tint = AppColors.onBackground,
            modifier = Modifier.size(18.dp)
        )

        Spacer(Modifier.width(SpaceBy.spaceBy18))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(AppColors.primary),
            textStyle = TextStyle(
                color = AppColors.onPrimary,
                fontSize = 14.sp,
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                if (value.isBlank()) {
                    Text(
                        text = stringResource(SEARCH),
                        color = AppColors.search,
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
        color = AppColors.onBackground,
        shape = Radius.radius12
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.padding20, vertical = Padding.padding20),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(imageUrl = chat.image)

                Spacer(Modifier.width(SpaceBy.spaceBy20))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chat.owner,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = FontSize.fontSize14,
                        color = AppColors.onPrimary
                    )

                    Spacer(Modifier.height(2.dp))

                    val subtitle = when (chat.lastMessageType.lowercase()) {
                        stringResource(VOICE) -> stringResource(SENT_A_VOICE_MESSAGE)
                        stringResource(ATTACHMENT) -> stringResource(SENT_AN_ATTACHMENT)
                        else -> chat.lastMessage
                    }

                    Text(
                        text = subtitle,
                        fontWeight = FontWeight.Normal,
                        fontSize = FontSize.fontSize12,
                        color = AppColors.onPrimary,
                        maxLines = 1
                    )
                }

                Spacer(Modifier.width(SpaceBy.spaceBy18))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = chat.lastActive,
                        fontSize = FontSize.fontSize12,
                        color = AppColors.onPrimary
                    )

                    Spacer(Modifier.height(6.dp))

                    if (chat.unreadMessages > 0) {
                        UnreadBadge(chat.unreadMessages)
                    } else {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Dot(); Dot(); Dot()
                        }
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(
                    start = Padding.padding80,
                    end = Padding.padding20,
                ),
                thickness = 0.5.dp,
                color = AppColors.onPrimary.copy(alpha = 0.15f)
            )
        }
    }
}


@Composable
private fun Avatar(
    imageUrl: String?,
) {
    val model = if (imageUrl.isNullOrBlank()) {
        IC_DEFAULT_AVATAR
    } else {
        ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    Box(
        modifier = IconSize.iconSize58,
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncImage(
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(AppColors.divider),
            error = painterResource(R.drawable.ic_default_avatar),
            placeholder = painterResource(R.drawable.ic_default_avatar)
        )

    }
}


@Composable
private fun UnreadBadge(count: Int) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(AppColors.secondary)
            .padding(horizontal = Padding.padding8, vertical = Padding.padding3),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            fontSize = FontSize.fontSize11,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.onPrimary
        )
    }
}

@Composable
private fun Dot() {
    Box(
        modifier = Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(AppColors.onPrimary)
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


