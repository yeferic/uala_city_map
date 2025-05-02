package com.yeferic.desingsystem.atomicdesign.atoms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UalaTextFieldWidgetKtTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = createComposeRule()

    private fun ruleTest(text: String) {
        rule.setContent {
            UalaTextFieldWidget(
                value = text,
                onValueChange = {},
                leftIcon = Icons.Default.Add,
                rightIcon = Icons.Default.Search,
            )
        }
    }

    @Test
    fun ualaTextFieldWidget_should_show_text() =
        runTest(testDispatcher) {
            // Given
            val text = "Test"
            ruleTest(text = text)

            // Then
            rule
                .onNodeWithTag(
                    testTag = UalaTextFieldWidgetTestTags.UALA_TEXT_FIELD_WIDGET,
                    useUnmergedTree = true,
                ).assertExists()

            rule.onNodeWithText(text = text).assertExists()
        }

    @Test
    fun ualaTextFieldWidget_should_show_left_icon() =
        runTest(testDispatcher) {
            // Given
            val text = "Test left icon"
            ruleTest(text = text)

            // Then
            rule
                .onNodeWithTag(
                    testTag = UalaTextFieldWidgetTestTags.UALA_TEXT_FIELD_WIDGET_LEFT_ICON,
                    useUnmergedTree = true,
                ).assertExists()
        }

    @Test
    fun ualaTextFieldWidget_should_show_right_icon() =
        runTest(testDispatcher) {
            // Given
            val text = "Test right icon"
            ruleTest(text = text)

            // Then
            rule
                .onNodeWithTag(
                    testTag = UalaTextFieldWidgetTestTags.UALA_TEXT_FIELD_WIDGET_RIGHT_ICON,
                    useUnmergedTree = true,
                ).assertExists()
        }
}
