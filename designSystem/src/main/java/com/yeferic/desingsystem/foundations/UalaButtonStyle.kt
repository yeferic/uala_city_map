package com.yeferic.desingsystem.foundations

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.yeferic.desingsystem.tokens.backgroundBlue
import com.yeferic.desingsystem.tokens.white

sealed class UalaButtonStyle(
    val shape: Shape,
    val colors: @Composable () -> ButtonColors,
    val textStyle: UalaTextStyle,
) {
    data object PrimaryButton : UalaButtonStyle(
        shape = RoundedCornerShape(4.dp),
        colors = {
            ButtonDefaults.buttonColors(
                containerColor = backgroundBlue,
                contentColor = white,
            )
        },
        textStyle = UalaTextStyle.PrimaryButtonText,
    )

    data object TertiaryButton : UalaButtonStyle(
        shape = RoundedCornerShape(4.dp),
        colors = {
            ButtonDefaults.buttonColors(
                containerColor = white,
                contentColor = backgroundBlue,
            )
        },
        textStyle = UalaTextStyle.TertiaryButtonText,
    )
}
