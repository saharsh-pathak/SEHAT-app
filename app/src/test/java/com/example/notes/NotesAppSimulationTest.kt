package com.example.notes

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.notes.ui.NotesApp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NotesAppSimulationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyStateAndFabClick() {
        composeTestRule.setContent {
            NotesApp()
        }

        // Verify empty state text
        composeTestRule.onNodeWithText("No notes yet").assertExists()

        // Click the Floating Action Button to add a new note
        composeTestRule.onNodeWithContentDescription("New note").performClick()

        // Verify the editor dialog opens
        composeTestRule.onNodeWithText("New note").assertExists()
    }
}
