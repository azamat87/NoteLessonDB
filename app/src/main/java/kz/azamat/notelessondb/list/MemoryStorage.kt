package kz.azamat.notelessondb.list

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kz.azamat.notelessondb.Note
import java.util.*

class MemoryStorage {

    private val notes = MutableStateFlow(listOf<Note>())

    fun getAllNotes(): Flow<List<Note>> = notes

    fun getNoteById(id: Long): Note? =
        notes.value.firstOrNull { it.id == id }

    fun create(title: String, note: String) {
        val lastId = notes.value.maxByOrNull { it.id }?.id ?: 0
        val newNote = Note(
            id = lastId + 1,
            title = title,
            text = note,
            date = Date()
        )

        val currentNotes = notes.value.toMutableList()
        currentNotes.add(newNote)

        notes.value = currentNotes
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun update(note: Note) {
        val currentNotes = notes.value.toMutableList()

        currentNotes.replaceAll { element ->
            if (element.id == note.id) note else element
        }

        notes.value = currentNotes
    }
}