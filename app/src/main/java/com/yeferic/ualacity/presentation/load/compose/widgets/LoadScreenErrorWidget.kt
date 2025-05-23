package com.yeferic.ualacity.presentation.load.compose.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yeferic.desingsystem.atomicdesign.atoms.UalaButton
import com.yeferic.desingsystem.atomicdesign.atoms.UalaLottieWidget
import com.yeferic.desingsystem.atomicdesign.atoms.UalaTextWidget
import com.yeferic.desingsystem.foundations.UalaButtonStyle
import com.yeferic.desingsystem.foundations.UalaTextStyle
import com.yeferic.desingsystem.tokens.white
import com.yeferic.ualacity.R

@Composable
fun LoadScreenErrorWidget(onClickAction: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UalaLottieWidget(
            modifier = Modifier.size(96.dp),
            id = com.yeferic.desingsystem.R.raw.error,
            velocity = 0.5f,
        )
        UalaTextWidget(
            text = stringResource(R.string.loadscreen_error),
            textAlign = TextAlign.Center,
            style = UalaTextStyle.BodyThin,
            color = white,
        )
        Spacer(modifier = Modifier.size(8.dp))
        UalaButton(
            text = stringResource(R.string.loadscreen_cta),
            style = UalaButtonStyle.TertiaryButton,
        ) {
            onClickAction()
        }
    }
}
