package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition

internal data object UalaLottieWidgetTestTags {
    const val UALA_LOTTIE_WIDGET = "uala_lottie_widget"
}

@Composable
fun UalaLottieWidget(
    modifier: Modifier = Modifier,
    @RawRes id: Int,
    iterations: Int = LottieConstants.IterateForever,
    velocity: Float = 1f,
    onAnimationFinished: (() -> Unit?)? = null,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(id))

    val animatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        if (composition != null) {
            animatable.animate(
                composition = composition,
                iterations = iterations,
                continueFromPreviousAnimate = false,
                speed = velocity,
            )
            onAnimationFinished?.invoke()
        }
    }

    LottieAnimation(
        modifier = modifier.testTag(UalaLottieWidgetTestTags.UALA_LOTTIE_WIDGET),
        composition = composition,
        progress = { animatable.progress },
    )
}
