package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.yeferic.desingsystem.foundations.UalaButtonStyle

internal data object UalaButtonTestTags {
    const val UALA_BUTTON_WIDGET = "uala_button_widget"
}

@Composable
fun UalaButton(
    modifier: Modifier = Modifier,
    style: UalaButtonStyle,
    text: String,
    textColor: Color? = null,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.testTag(UalaButtonTestTags.UALA_BUTTON_WIDGET),
        shape = style.shape,
        colors = style.colors(),
        onClick = onClick,
    ) {
        UalaTextWidget(
            text = text,
            style = style.textStyle,
            color = textColor,
        )
    }
}
