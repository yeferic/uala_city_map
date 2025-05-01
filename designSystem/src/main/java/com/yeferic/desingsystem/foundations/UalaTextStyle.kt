package com.yeferic.desingsystem.foundations

import androidx.compose.ui.text.TextStyle
import com.yeferic.desingsystem.tokens.RobotoFontFamily
import com.yeferic.desingsystem.tokens.backgroundBlue
import com.yeferic.desingsystem.tokens.textLightWeight
import com.yeferic.desingsystem.tokens.textMediumSize
import com.yeferic.desingsystem.tokens.textNormalSize
import com.yeferic.desingsystem.tokens.textNormalWeight
import com.yeferic.desingsystem.tokens.textThinWeight
import com.yeferic.desingsystem.tokens.white

sealed class UalaTextStyle(
    val style: TextStyle,
) {
    data object BodyThin : UalaTextStyle(
        TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = textThinWeight,
            fontSize = textNormalSize,
        ),
    )

    data object Light : UalaTextStyle(
        TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = textLightWeight,
            fontSize = textNormalSize,
        ),
    )

    data object Normal : UalaTextStyle(
        TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = textNormalWeight,
            fontSize = textNormalSize,
        ),
    )

    data object PrimaryButtonText : UalaTextStyle(
        TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = textNormalWeight,
            fontSize = textNormalSize,
            color = white,
        ),
    )

    data object TertiaryButtonText : UalaTextStyle(
        TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = textNormalWeight,
            fontSize = textNormalSize,
            color = backgroundBlue,
        ),
    )

    data object TextFieldSearch : UalaTextStyle(
        TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = textNormalWeight,
            fontSize = textMediumSize,
            color = white,
        ),
    )

    data object TextFieldSearchPlaceHolder : UalaTextStyle(
        TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = textThinWeight,
            fontSize = textMediumSize,
            color = white.copy(alpha = 0.2f),
        ),
    )
}
