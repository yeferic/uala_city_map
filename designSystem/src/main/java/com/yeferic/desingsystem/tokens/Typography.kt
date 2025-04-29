package com.yeferic.desingsystem.tokens

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yeferic.desingsystem.R

val RobotoFontFamily =
    FontFamily(
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_bold, FontWeight.Bold),
    )

// Sizes
val textSmallSize = 8.sp
val textNormalSize = 12.sp
val textMediumSize = 14.sp
val textLargeSize = 16.sp
val textXLargeSize = 20.sp
val textXXLargeSize = 24.sp
val textXXXLargeSize = 32.sp

// Weights
val textThinWeight = FontWeight.Thin
val textLightWeight = FontWeight.Light
val textNormalWeight = FontWeight.Normal
val textMediumWeight = FontWeight.Medium
val textBoldWeight = FontWeight.Bold

val Typography =
    Typography(
        bodyLarge =
            TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
    )
