package com.yeferic.ualacity.presentation.load.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yeferic.desingsystem.tokens.backgroundBlue
import com.yeferic.ualacity.presentation.load.compose.widgets.LoadScreenErrorWidget
import com.yeferic.ualacity.presentation.load.compose.widgets.LoadScreenLoadingWidget
import com.yeferic.ualacity.presentation.load.compose.widgets.LoadScreenStartWidget
import com.yeferic.ualacity.presentation.load.viewmodels.LoadViewModel
import kotlinx.coroutines.delay

private const val ANIMATION_DURATION = 1000
private const val DELAY_TO_GO_TO_NEXT_SCREEN = 1000L

@Composable
fun LoadScreen(
    viewModel: LoadViewModel = hiltViewModel(),
    navigateToMap: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var animationEnded by remember { mutableStateOf(false) }

    if (uiState.dataLoadedSuccessfully && !uiState.fetchRemoteData) {
        navigateToMap()
    } else if (uiState.fetchRemoteData) {
        LoadScreenContent {
            AnimatedVisibility(
                visible = uiState.error != null,
                enter = fadeIn(tween(ANIMATION_DURATION)),
                exit = fadeOut(tween(ANIMATION_DURATION)),
            ) {
                LoadScreenErrorWidget {
                    viewModel.getIsDataLoaded()
                }
            }

            AnimatedVisibility(
                visible = uiState.isLoading,
                enter = fadeIn(tween(ANIMATION_DURATION)),
                exit = fadeOut(tween(ANIMATION_DURATION)),
            ) {
                LoadScreenLoadingWidget()
            }

            AnimatedVisibility(
                visible = !uiState.isLoading && uiState.error == null,
                enter = fadeIn(tween(ANIMATION_DURATION)),
                exit = fadeOut(tween(ANIMATION_DURATION)),
            ) {
                LoadScreenStartWidget { animationEnded = true }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getIsDataLoaded()
    }

    if (animationEnded) {
        LaunchedEffect(Unit) {
            delay(DELAY_TO_GO_TO_NEXT_SCREEN)
            navigateToMap()
        }
    }
}

@Composable
private fun LoadScreenContent(content: @Composable () -> Unit) {
    ConstraintLayout(
        modifier =
            Modifier
                .background(backgroundBlue)
                .fillMaxSize(),
    ) {
        val (centerContent) = createRefs()
        Box(
            modifier =
                Modifier
                    .background(backgroundBlue)
                    .constrainAs(centerContent) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = parent.top, bottom = parent.bottom)
                    },
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
