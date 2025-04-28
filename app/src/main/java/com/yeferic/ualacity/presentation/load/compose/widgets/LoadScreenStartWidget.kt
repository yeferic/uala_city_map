package com.yeferic.ualacity.presentation.load.compose.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yeferic.desingsystem.atomicdesign.atoms.UalaLottieWidget
import com.yeferic.desingsystem.atomicdesign.atoms.UalaTextWidget
import com.yeferic.desingsystem.foundations.UalaTextStyle
import com.yeferic.desingsystem.tokens.white
import com.yeferic.ualacity.R

@Composable
fun LoadScreenStartWidget(onAnimationEnd: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UalaLottieWidget(
            modifier = Modifier.size(96.dp),
            id = com.yeferic.desingsystem.R.raw.done,
            iterations = 1,
        ) {
            onAnimationEnd()
        }
        UalaTextWidget(
            text = stringResource(R.string.loadscreen_done),
            textAlign = TextAlign.Center,
            style = UalaTextStyle.BodyThin,
            color = white,
        )
    }
}
