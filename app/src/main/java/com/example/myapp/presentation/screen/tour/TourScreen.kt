package com.example.myapp.presentation.screen.tour

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapp.presentation.common.UiRes.String.FULL_STAR
import com.example.myapp.presentation.common.UiRes.String.HALF_STAR
import com.example.myapp.presentation.common.UiRes.String.LOCATION
import com.example.myapp.presentation.common.UiRes.String.NUMBER
import com.example.myapp.presentation.common.UiRes.String.PRICE
import com.example.myapp.presentation.common.UiRes.String.RETRY
import com.example.myapp.presentation.common.UiRes.String.STATISTICS
import com.example.myapp.presentation.extension.TopTitle
import com.example.myapp.presentation.screen.theme.FontSize
import com.example.myapp.presentation.screen.theme.Height
import com.example.myapp.presentation.screen.theme.Padding
import com.example.myapp.presentation.screen.theme.Radius
import com.example.myapp.presentation.screen.theme.SpaceBy
import com.example.myapp.presentation.screen.theme.Spacing
import com.example.myapp.presentation.screen.theme.Weight
import com.example.myapp.presentation.screen.theme.error
import com.example.myapp.presentation.screen.theme.errorBackground
import com.example.myapp.presentation.screen.theme.loader
import com.example.myapp.presentation.screen.theme.white
import com.example.myapp.presentation.screen.tour.model.TourUi
import kotlinx.coroutines.flow.*
import kotlin.math.absoluteValue

@Composable
fun TourHomeScreen(
    onTourClick: (TourUi) -> Unit,
    viewModel: TourHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val isDark by viewModel.isDarkMode.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is TourHomeSideEffect.NavigateToTour -> {
                    uiState.tours.getOrNull(effect.index)?.let(onTourClick)
                }
            }
        }
    }

    TourHomeScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        isDark = isDark
    )
}


@Composable
private fun TourHomeScreenContent(
    uiState: TourHomeState,
    onEvent: (TourHomeEvent) -> Unit,
    isDark: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(Spacing.spacing14)


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.padding36),
                verticalAlignment = Alignment.CenterVertically
            ) {
                stringResource(STATISTICS).TopTitle()

                Spacer(Modifier.weight(weight = Weight.FILL))

                Switch(
                    checked = isDark,
                    onCheckedChange = { enabled ->
                        onEvent(TourHomeEvent.SetDarkMode(enabled))
                    }

                )
            }

            Spacer(Modifier.height(16.dp))

            if (uiState.loader) {
                Loader()
            }

            if (uiState.error != null) {
                ErrorBlock(message = uiState.error, onRetry = { onEvent(TourHomeEvent.OnRetryClick) })
                Spacer(Spacing.spacing12)
            }

            if (uiState.tours.isNotEmpty()) {
                ToursPager(
                    tours = uiState.tours,
                    onCardClick = { index ->
                        onEvent(TourHomeEvent.OnTourClick(index))
                    }
                )
            }
        }
    }
}

@Composable
private fun ToursPager(
    tours: List<TourUi>,
    onCardClick: (Int) -> Unit
) {
    val pagerState = rememberPagerState { tours.size }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = Padding.padding35),
        pageSpacing = SpaceBy.spaceBy5 ,
        modifier = Modifier.fillMaxWidth()
    ) { page ->

        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                .absoluteValue
                .coerceIn(0f, 1f)

        val scale = 0.86f + (1f - pageOffset) * 0.14f
        val alpha = 0.65f + (1f - pageOffset) * 0.35f

        TourCard(
            tour = tours[page],
            onClick = { onCardClick(page) },
            modifier = Modifier
                .height(Height.height520)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
        )
    }
}

@Composable
private fun TourCard(
    tour: TourUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = Radius.radius22,
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            TourImage(photoUrl = tour.photo)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.18f))
            )

            TourCardOverlay(
                location = tour.location,
                number = tour.number,
                title = tour.title,
                price = tour.price,
                stars = tour.stars
            )
        }
    }
}

@Composable
private fun TourImage(
    photoUrl: String?
) {
    val model = if (photoUrl.isNullOrBlank()) {
        null
    } else {
        ImageRequest.Builder(LocalContext.current)
            .data(photoUrl)
            .crossfade(true)
            .build()
    }

    AsyncImage(
        model = model,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun TourCardOverlay(
    location: String,
    number: Int,
    title: String,
    price: Int,
    stars: Int
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.padding18),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(LOCATION, location),
                color = white,
                fontSize = FontSize.fontSize14
            )

            Spacer(Modifier.weight(Weight.FILL))

            Text(
                text = stringResource(NUMBER, number),
                color = white,
                fontSize = FontSize.fontSize14
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Padding.padding18)
        ) {
            Text(
                text = title,
                color = white,
                fontSize = FontSize.fontSize30,
                fontWeight = FontWeight.SemiBold,
                lineHeight = Height.height32
            )

            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(PRICE, price),
                    color = white,
                    fontSize = FontSize.fontSize16,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.weight(Weight.FILL))

                RatingStars(stars)
            }
        }
    }
}

@Composable
private fun RatingStars(
    stars: Int
) {
    val full = stars.coerceIn(0, 5)
    val empty = 5 - full

    Row(horizontalArrangement = SpaceBy.spaceBy3) {
        repeat(full) {
            Text(
                stringResource(FULL_STAR),
                color = white,
                fontSize = FontSize.fontSize14
            )
        }
        repeat(empty) {
            Text(
                stringResource(HALF_STAR),
                color = white,
                fontSize = FontSize.fontSize14
            )
        }
    }
}

@Composable
private fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Padding.padding40),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = loader)
    }
}

@Composable
private fun ErrorBlock(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = Radius.radius14)
            .background(errorBackground)
            .padding(all = Padding.padding14)
    ) {
        Text(text = message, color = error, fontSize = FontSize.fontSize14)
        Spacer(Modifier.height(height = Height.height10))
        TextButton(onClick = onRetry) {
            Text(
                text = stringResource(RETRY),
                color = white
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TourHomePreview() {
    TourHomeScreenContent(
        uiState = TourHomeState(
            loader = false,
            tours = listOf(
                TourUi(
                    title = "Natural walk\nto the top",
                    location = "Barcelona",
                    number = 2500,
                    photo = "",
                    price = 120,
                    stars = 4
                ),
                TourUi(
                    title = "Sea view\nweekend",
                    location = "Nice",
                    number = 1800,
                    photo = "",
                    price = 90,
                    stars = 5
                )
            )
        ),
        onEvent = {},
        isDark = true
    )
}