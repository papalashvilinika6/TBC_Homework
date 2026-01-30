package com.example.myapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapp.presentation.screen.home.model.PostUi
import com.example.myapp.presentation.screen.home.model.StoryUi
import com.example.myapp.presentation.screen.theme.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {

    }

    HomeScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = Spacing.spacing16)
            .padding(top = Spacing.spacing16)
    ) {
        StoriesRow(
            stories = state.stories,
            onStoryClick = { onEvent(HomeEvent.OnStoryClick(it)) }
        )

        Spacer(modifier = Modifier.height(Spacing.spacing14))

        val firstPost = state.posts.firstOrNull()
        if (firstPost != null) {
            PostCard(
                post = firstPost,
                onLike = { onEvent(HomeEvent.OnLikeClick(firstPost.id)) },
                onComment = { onEvent(HomeEvent.OnPostClick(firstPost.id)) },
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(Radius.radius24))
                    .background(colors.surface),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = if (state.isLoading) "Loading..." else (state.errorMessage ?: "No posts"),
                    style = typography.bodyMedium,
                    color = colors.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.height(Spacing.spacing12))
    }
}


@Composable
private fun StoriesRow(
    stories: List<StoryUi>,
    onStoryClick: (Int) -> Unit
) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing14)
    ) {
        stories.forEach { story ->
            Box(
                modifier = Modifier
                    .width(Height.storyWidth)
                    .height(Height.storyHeight)
                    .clip(RoundedCornerShape(Radius.radius24))
                    .background(colors.surface)
                    .clickable { onStoryClick(story.id) }
            ) {
                AsyncImage(
                    model = story.cover,
                    contentDescription = story.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(androidx.compose.ui.graphics.Color(0x22000000))
                )

                androidx.compose.material3.Text(
                    text = story.title,
                    style = typography.titleMedium,
                    color = white,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(Spacing.spacing14)
                )
            }
        }
    }
}

@Composable
private fun PostCard(
    post: PostUi,
    onLike: () -> Unit,
    onComment: () -> Unit,
) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.radius24))
            .background(colors.surface)
            .padding(Spacing.spacing16)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AvatarCircle(
                imageUrl = post.avatar,
                size = IconSize.avatar42
            )

            Spacer(modifier = Modifier.width(Spacing.spacing12))

            Column(modifier = Modifier.weight(1f)) {
                androidx.compose.material3.Text(
                    text = post.fullName,
                    style = typography.titleMedium,
                    color = colors.onSurface
                )
                androidx.compose.material3.Text(
                    text = post.postDate,
                    style = typography.bodySmall,
                    color = colors.muted
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.spacing12))

        if (!post.postDesc.isNullOrBlank()) {
            androidx.compose.material3.Text(
                text = post.postDesc,
                style = typography.bodyMedium,
                color = colors.onSurface
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
        }

        PostImagesGrid(images = post.images)

        Spacer(modifier = Modifier.height(Spacing.spacing14))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionPill(text = "${post.commentsCount} Comments", onClick = onComment)
            Spacer(modifier = Modifier.width(Spacing.spacing14))
            ActionPill(text = "${post.likesCount} Likes", onClick = onLike)
            Spacer(modifier = Modifier.weight(1f))
            ActionPill(text = "Share", onClick = {})
        }

        Spacer(modifier = Modifier.height(Spacing.spacing14))

        CommentInput()
    }
}

@Composable
private fun PostImagesGrid(images: List<String>) {
    val colors = AppThemeProvider.colors

    if (images.isEmpty()) return

    val shape = RoundedCornerShape(Radius.radius20)

    when (images.size) {
        1 -> {
            AsyncImage(
                model = images[0],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(shape)
            )
        }

        2 -> {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing12)) {
                images.take(2).forEach { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp)
                            .clip(shape)
                    )
                }
            }
        }

        else -> {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing12)) {
                AsyncImage(
                    model = images[0],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                        .clip(shape)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.spacing12)
                ) {
                    AsyncImage(
                        model = images[1],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .clip(shape)
                    )
                    AsyncImage(
                        model = images[2],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .clip(shape)
                    )
                }
            }
        }
    }
}

@Composable
private fun AvatarCircle(
    imageUrl: String?,
    size: androidx.compose.ui.unit.Dp
) {
    val colors = AppThemeProvider.colors
    val shape = RoundedCornerShape(999.dp)

    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(colors.primary.copy(alpha = 0.25f)),
        contentAlignment = Alignment.Center
    ) {
        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            androidx.compose.material3.Text(
                text = "🙂",
                modifier = Modifier,
                color = colors.onSurface
            )
        }
    }
}

@Composable
private fun ActionPill(
    text: String,
    onClick: () -> Unit
) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Radius.radius16))
            .background(colors.background)
            .clickable { onClick() }
            .padding(horizontal = Spacing.spacing12, vertical = Spacing.spacing8)
    ) {
        androidx.compose.material3.Text(
            text = text,
            style = typography.bodySmall,
            color = colors.muted
        )
    }
}

@Composable
private fun CommentInput() {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    var value by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.radius20))
            .background(colors.background)
            .padding(horizontal = Spacing.spacing12, vertical = Spacing.spacing10)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(colors.primary.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text("🙂", style = typography.bodySmall, color = colors.onSurface)
        }

        Spacer(modifier = Modifier.width(Spacing.spacing10))

        BasicTextField(
            value = value,
            onValueChange = { value = it },
            textStyle = TextStyle(color = colors.onSurface),
            cursorBrush = SolidColor(colors.primary),
            modifier = Modifier.weight(1f),
            decorationBox = { inner ->
                if (value.isBlank()) {
                    androidx.compose.material3.Text(
                        text = "Write comment...",
                        style = typography.bodyMedium,
                        color = colors.muted
                    )
                }
                inner()
            }
        )

        Spacer(modifier = Modifier.width(Spacing.spacing10))

        androidx.compose.material3.Text(
            text = "📎",
            style = typography.bodyLarge,
            color = colors.muted
        )
    }
}


