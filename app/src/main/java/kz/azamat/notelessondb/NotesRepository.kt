package kz.azamat.notelessondb

import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kz.azamat.notelessondb.database.NotesDb
import java.util.*


object NotesRepository {

    private val db =
        Room.databaseBuilder(
            NoteApplication.context,
            NotesDb::class.java,
            "note_db").build()

    private val storage = db.notesDao()

    fun getAllNotes(): Flow<List<Note>> = storage.getAllNotes()

    suspend fun create(title: String, note: String) {
        val entity = Note(
            title = title,
            text = note,
            date = Date()
        )
        storage.insert(entity)
    }

    suspend fun update(note: Note) {
        storage.update(note)
    }

    suspend fun get(id: Long): Note? = storage.getNoteById(id)

}