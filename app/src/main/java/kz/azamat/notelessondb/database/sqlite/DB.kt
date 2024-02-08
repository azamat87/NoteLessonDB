package kz.azamat.notelessondb.database.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.azamat.notelessondb.Note
import java.util.*

class DB(
    context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val db = NotesDBHelper(context).writableDatabase

    suspend fun addNote(title: String, note: String) = withContext(ioDispatcher) {
        val values = ContentValues().apply {
            put(NotesDbContract.COLUMN_NAME_TITLE, title)
            put(NotesDbContract.COLUMN_NAME_NOTE, note)
            put(NotesDbContract.COLUMN_NAME_DATE, Date().time)
        }

        db.insert(NotesDbContract.TABLE_NAME, null, values)
    }

    suspend fun getNote(id: Long): Note? = withContext(ioDispatcher) {
        val projection = arrayOf(
            BaseColumns._ID,
            NotesDbContract.COLUMN_NAME_TITLE,
            NotesDbContract.COLUMN_NAME_NOTE,
            NotesDbContract.COLUMN_NAME_DATE,
        )
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")

        val cursor = db.query(
            NotesDbContract.TABLE_NAME,
            projection,
            selection,
            selectionArgs, null, null, null
        )

        return@withContext cursor?.use { cursor ->
            if (cursor.moveToFirst()) parceNote(cursor) else null
        }
    }

    private fun parceNote(cursor: Cursor): Note {
        val timeStamp =
            cursor.getLong(cursor.getColumnIndexOrThrow(NotesDbContract.COLUMN_NAME_DATE))

        return Note(
            id = cursor.getLong(
                cursor.getColumnIndexOrThrow(BaseColumns._ID)),
            title = cursor.getString(
                cursor.getColumnIndexOrThrow(NotesDbContract.COLUMN_NAME_TITLE)),
            text = cursor.getString(
                cursor.getColumnIndexOrThrow(NotesDbContract.COLUMN_NAME_NOTE)),
            date = Date(timeStamp)
        )
    }

    suspend fun getAllNotes(): List<Note> = withContext(ioDispatcher) {
        val projection = arrayOf(
            BaseColumns._ID,
            NotesDbContract.COLUMN_NAME_TITLE,
            NotesDbContract.COLUMN_NAME_NOTE,
            NotesDbContract.COLUMN_NAME_DATE,
        )

        val cursor = db.query(
            NotesDbContract.TABLE_NAME,
            projection,
            null, null, null, null, null
        )

        val result = mutableListOf<Note>()

        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val note = parceNote(cursor)
                    result.add(note)
                } while (cursor.moveToNext())
            }
        }

        return@withContext result
    }

    suspend fun update(note: Note) = withContext(ioDispatcher) {
        val values = ContentValues().apply {
            put(NotesDbContract.COLUMN_NAME_TITLE, note.title)
            put(NotesDbContract.COLUMN_NAME_NOTE, note.text)
            put(NotesDbContract.COLUMN_NAME_DATE, note.date.time)
        }
        val clause = "${BaseColumns._ID} = ?"
        val args = arrayOf("${note.id}")

        db.update(
            NotesDbContract.TABLE_NAME,
            values,
            clause,
            args
        )
    }

    suspend fun delete(note: Note) = withContext(ioDispatcher) {
        val clause = "${BaseColumns._ID} = ?"
        val args = arrayOf("${note.id}")

        db.delete(
            NotesDbContract.TABLE_NAME,
            clause,
            args
        )
    }

}