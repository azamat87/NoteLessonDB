package kz.azamat.notelessondb

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import kz.azamat.notelessondb.list.NotesListFragment

object NoteDetailScreen : KScreen<NoteDetailScreen>() {

    private val titleEd = KEditText{ withId(R.id.title_et) }
    private val noteEd = KEditText{ withId(R.id.note_et) }

    fun inputNoteData(title: String, note: String) {
        titleEd.typeText(title)
        noteEd.typeText(note)
        pressBack()
        pressBack()
    }

    override val layoutId: Int
        get() = R.layout.fragment_notes_detail
    override val viewClass: Class<*>
        get() = NotesListFragment::class.java
}