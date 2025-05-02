package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.yeferic.desingsystem.foundations.UalaTextStyle
import com.yeferic.desingsystem.tokens.backgroundBlue
import com.yeferic.desingsystem.tokens.white

internal object UalaTextFieldWidgetTestTags {
    const val UALA_TEXT_FIELD_WIDGET = "ualaTextFieldWidget"
    const val UALA_TEXT_FIELD_WIDGET_LEFT_ICON = "ualaTextFieldWidgetLeftIcon"
    const val UALA_TEXT_FIELD_WIDGET_RIGHT_ICON = "ualaTextFieldWidgetRightIcon"
}

@Composable
fun UalaTextFieldWidget(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
    rightIconColor: Color = white,
    onFocus: (() -> Unit)? = null,
    onFocusLost: (() -> Unit)? = null,
) {
    var hasFocus by remember { mutableStateOf(true) }

    Row(
        modifier =
            Modifier
                .background(
                    color = backgroundBlue,
                    shape = RoundedCornerShape(4.dp),
                ).padding(8.dp)
                .testTag(UalaTextFieldWidgetTestTags.UALA_TEXT_FIELD_WIDGET),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leftIcon != null) {
            IconButton(
                modifier =
                    Modifier
                        .size(
                            24.dp,
                        ).testTag(UalaTextFieldWidgetTestTags.UALA_TEXT_FIELD_WIDGET_LEFT_ICON),
                onClick = { onLeftIconClick?.invoke() },
            ) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = "leftIcon",
                    tint = white,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        BasicTextField(
            modifier =
                modifier
                    .onFocusChanged { focusState ->
                        if (hasFocus && !focusState.isFocused) {
                            hasFocus = false
                            onFocusLost?.invoke()
                        }
                        if (focusState.isFocused) {
                            hasFocus = true
                            onFocus?.invoke()
                        }
                    }.weight(1f),
            textStyle = UalaTextStyle.TextFieldSearch.style,
            cursorBrush = SolidColor(white),
            value = value,
            onValueChange = onValueChange,
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        UalaTextWidget(
                            text = placeholder,
                            style = UalaTextStyle.TextFieldSearchPlaceHolder,
                        )
                    }
                    innerTextField()
                }
            },
        )

        if (value.isNotEmpty() && rightIcon != null) {
            IconButton(
                modifier =
                    Modifier
                        .size(
                            24.dp,
                        ).testTag(UalaTextFieldWidgetTestTags.UALA_TEXT_FIELD_WIDGET_RIGHT_ICON),
                onClick = {
                    onRightIconClick?.invoke()
                },
            ) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = "rightIcon",
                    tint = rightIconColor,
                )
            }
        }
    }
}
