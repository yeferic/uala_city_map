package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import com.yeferic.desingsystem.foundations.UalaTextStyle

internal data object UalaTextWidgetTestTags {
    const val UALA_TEXT_WIDGET = "uala_text_widget"
}

@Composable
fun UalaTextWidget(
    modifier: Modifier = Modifier,
    text: String,
    style: UalaTextStyle = UalaTextStyle.Normal,
    color: Color? = null,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier.testTag(UalaTextWidgetTestTags.UALA_TEXT_WIDGET),
        text = text,
        style = style.style,
        color = color ?: style.style.color,
        textAlign = textAlign,
    )
}
