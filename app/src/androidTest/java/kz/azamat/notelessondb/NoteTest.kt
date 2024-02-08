package kz.azamat.notelessondb

import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import androidx.test.ext.junit.rules.activityScenarioRule
import kotlinx.coroutines.delay
import org.junit.Test
import java.time.Duration

class NoteTest : TestCase() {

    @get:Rule
    val activity = activityScenarioRule<MainActivity>()

    @Test
    fun noteTest() = run {

        step("Navigate to add note screen") {
            MainScreen.clickAddNote()
        }

        step("Add new note and back") {
            NoteDetailScreen {
                inputNoteData("Title first", "Note text")
            }
        }

        step("Delete first note") {
            MainScreen {
                noteRv {
                    childAt<MainScreen.NoteItem>(0) {
                        tvNoteTitle.hasAnyText()
                        tvNoteText.hasAnyText()
                        deleteIcon.click()
                    }
                }
            }
        }
    }
}