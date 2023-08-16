package kz.azamat.notelessondb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kz.azamat.notelessondb.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

}