package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView

internal data object UalaAnimationWidgetTestTags {
    const val UALA_ANIMATION_WIDGET = "uala_animation_widget"
}

@Composable
fun UalaAnimationWidget(
    modifier: Modifier = Modifier,
    @RawRes animationRes: Int,
    stateMachineName: String? = null,
) {
    AndroidView(
        modifier = modifier.testTag(UalaAnimationWidgetTestTags.UALA_ANIMATION_WIDGET),
        factory = { context ->
            RiveAnimationView(context).also {
                it.setRiveResource(
                    resId = animationRes,
                    stateMachineName = stateMachineName,
                    autoplay = true,
                )
            }
        },
    )
}
