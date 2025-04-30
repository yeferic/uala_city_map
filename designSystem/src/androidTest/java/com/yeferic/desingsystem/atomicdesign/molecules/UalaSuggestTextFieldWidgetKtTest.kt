package com.yeferic.desingsystem.atomicdesign.molecules

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UalaSuggestTextFieldWidgetKtTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = createComposeRule()

    private fun ruleTest(
        text: String,
        items: List<UalaSuggestTextFieldWidgetItem>,
    ) {
        rule.setContent {
            UalaSuggestTextFieldWidget(
                value = text,
                onValueChange = {},
                items = items,
                onSelected = {},
            )
        }
    }

    @Test
    fun ualaSuggestTextFieldWidget_should_be_shown() =
        runTest(testDispatcher) {
            // Given
            val text = "test_tag"
            ruleTest(text, emptyList())

            // Then
            rule
                .onNodeWithTag(
                    testTag = UalaSuggestTextFieldWidgetTestTags.UALA_SUGGEST_TEXT_FIELD_WIDGET,
                    useUnmergedTree = true,
                ).assertExists()
        }

    @Test
    fun ualaSuggestTextFieldWidget_should_show_list() =
        runTest(testDispatcher) {
            // Given
            val text = "test"
            ruleTest(text, listOf(UalaSuggestTextFieldWidgetItem("id", "text")))

            // Then
            rule
                .onNodeWithTag(
                    testTag =
                        UalaSuggestTextFieldWidgetTestTags.UALA_SUGGEST_TEXT_FIELD_WIDGET,
                    useUnmergedTree = true,
                ).assertExists()
        }
}
