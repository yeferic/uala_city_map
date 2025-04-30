package com.yeferic.desingsystem.atomicdesign.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.yeferic.desingsystem.R
import com.yeferic.desingsystem.atomicdesign.atoms.UalaLottieWidget
import com.yeferic.desingsystem.atomicdesign.atoms.UalaTextFieldWidget
import com.yeferic.desingsystem.atomicdesign.atoms.UalaTextWidget
import com.yeferic.desingsystem.tokens.backgroundBlue
import com.yeferic.desingsystem.tokens.white

internal data object UalaSuggestTextFieldWidgetTestTags {
    const val UALA_SUGGEST_TEXT_FIELD_WIDGET = "UalaSuggestTextFieldWidget"
    const val UALA_SUGGEST_TEXT_FIELD_WIDGET_LIST = "UalaSuggestTextFieldWidgetList"
}

data class UalaSuggestTextFieldWidgetItem(
    val id: String,
    val text: String,
    val icon: ImageVector? = null,
)

@Composable
fun UalaSuggestTextFieldWidget(
    modifier: Modifier = Modifier,
    modifierList: Modifier = Modifier,
    modifierTextField: Modifier = Modifier,
    isLoading: Boolean = false,
    value: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    rightIconColor: Color = white,
    items: List<UalaSuggestTextFieldWidgetItem>,
    itemSelected: UalaSuggestTextFieldWidgetItem? = null,
    onSelected: (UalaSuggestTextFieldWidgetItem) -> Unit,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null,
    onFocus: (() -> Unit)? = null,
    onFocusLost: (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    var hasFocus by remember { mutableStateOf(true) }

    Column(
        modifier =
            modifier.testTag(
                UalaSuggestTextFieldWidgetTestTags.UALA_SUGGEST_TEXT_FIELD_WIDGET,
            ),
    ) {
        UalaTextFieldWidget(
            modifier = modifierTextField,
            value = value,
            placeholder = placeholder,
            onValueChange = {
                onValueChange(it)
            },
            leftIcon = leftIcon,
            rightIcon = if (itemSelected != null) rightIcon else null,
            onLeftIconClick = onLeftIconClick,
            onRightIconClick = onRightIconClick,
            rightIconColor = itemSelected?.icon?.let { rightIconColor } ?: white.copy(0.2f),
            onFocus = {
                hasFocus = true
                onFocus?.invoke()
            },
            onFocusLost = {
                hasFocus = false
                onFocusLost?.invoke()
            },
        )

        Spacer(modifier = Modifier.size(2.dp))
        AnimatedVisibility(
            visible = hasFocus && value.isNotEmpty(),
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300)),
        ) {
            LazyColumn(
                modifier =
                    modifierList
                        .background(
                            color = white,
                            shape = RoundedCornerShape(4.dp),
                        ).testTag(
                            UalaSuggestTextFieldWidgetTestTags.UALA_SUGGEST_TEXT_FIELD_WIDGET_LIST,
                        ),
            ) {
                if (isLoading) {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .align(
                                        alignment = Alignment.CenterHorizontally,
                                    ).padding(8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            UalaLottieWidget(
                                modifier =
                                    Modifier
                                        .width(64.dp)
                                        .height(24.dp),
                                id = R.raw.searching,
                            )
                        }
                    }
                } else {
                    items(items) { item ->
                        Row(
                            modifier =
                                Modifier
                                    .clickable {
                                        onSelected(item)
                                        focusManager.clearFocus()
                                    }.padding(16.dp),
                        ) {
                            UalaTextWidget(
                                modifier = Modifier.weight(1f),
                                text = item.text,
                            )
                            if (item.icon != null) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = backgroundBlue,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
