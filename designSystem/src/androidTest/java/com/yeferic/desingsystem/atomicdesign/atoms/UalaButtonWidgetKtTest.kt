package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.yeferic.desingsystem.foundations.UalaButtonStyle
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UalaButtonWidgetKtTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = createComposeRule()

    private fun ruleTest(text: String) {
        rule.setContent {
            UalaButton(
                text = text,
                style = UalaButtonStyle.PrimaryButton,
                onClick = {},
            )
        }
    }

    @Test
    fun ualaButtonWidget_should_be_shown() =
        runTest(testDispatcher) {
            // Given
            val text = "test_tag"
            ruleTest(text)

            // Then
            rule
                .onNodeWithTag(
                    testTag = UalaButtonTestTags.UALA_BUTTON_WIDGET,
                    useUnmergedTree = true,
                ).assertExists()
        }

    @Test
    fun ualaButtonWidget_should_show_text() =
        runTest(testDispatcher) {
            // Given
            val text = "test"
            ruleTest(text)

            // Then
            rule.onNodeWithText(text).assertExists()
        }
}
