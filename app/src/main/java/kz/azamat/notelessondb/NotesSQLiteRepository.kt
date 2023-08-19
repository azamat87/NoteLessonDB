package kz.azamat.notelessondb

import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kz.azamat.notelessondb.database.NotesDb
import kz.azamat.notelessondb.database.sqlite.DB
import java.util.*


object NotesSQLiteRepository {

    private val db = DB(NoteApplication.context)

    suspend fun getAllNotes(): List<Note> = db.getAllNotes()

    suspend fun create(title: String, note: String) {
        db.addNote(title, note)
    }

    suspend fun update(note: Note) {
        db.update(note)
    }

    suspend fun get(id: Long): Note? = db.getNote(id)

}