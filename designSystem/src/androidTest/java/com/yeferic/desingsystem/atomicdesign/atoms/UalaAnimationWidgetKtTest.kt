package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.yeferic.desingsystem.R
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UalaAnimationWidgetKtTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = createComposeRule()

    private fun ruleTest() {
        rule.setContent {
            UalaAnimationWidget(
                animationRes = R.raw.gps,
            )
        }
    }

    @Test
    fun ualaAnimationWidget_should_be_show_animation() =
        runTest(testDispatcher) {
            // Given
            ruleTest()

            // Then
            rule
                .onNodeWithTag(
                    testTag = UalaAnimationWidgetTestTags.UALA_ANIMATION_WIDGET,
                    useUnmergedTree = true,
                ).assertExists()
        }
}
