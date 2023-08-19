package kz.azamat.notelessondb.database.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class NotesDBHelper (context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DB_CREATE_NOTES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        /* no migrations */
    }

    companion object {
        private const val DB_NAME = "notes_db"
        private const val DB_VERSION = 1

        private const val DB_CREATE_NOTES =
            "CREATE TABLE ${NotesDbContract.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${NotesDbContract.COLUMN_NAME_TITLE} TEXT," +
                    "${NotesDbContract.COLUMN_NAME_NOTE} TEXT," +
                    "${NotesDbContract.COLUMN_NAME_DATE} INTEGER DEFAULT CURRENT_TIMESTAMP NOT NULL)"
    }
}