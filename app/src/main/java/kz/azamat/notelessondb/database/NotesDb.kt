package kz.azamat.notelessondb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.azamat.notelessondb.Note

@Database(version = 1, entities = [Note::class])
@TypeConverters(DateConverter::class)
abstract class NotesDb: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}