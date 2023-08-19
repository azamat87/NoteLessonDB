package kz.azamat.notelessondb.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room
import kz.azamat.notelessondb.NoteApplication
import kz.azamat.notelessondb.database.NotesDb
import kz.azamat.notelessondb.database.sqlite.NotesDBHelper

class NotesContentProvider: ContentProvider() {

    private lateinit var authority: String

    private val db by lazy { NotesDBHelper(context!!).readableDatabase }

    override fun onCreate(): Boolean {

        return true
    }

    override fun attachInfo(context: Context?, info: ProviderInfo?) {
        super.attachInfo(context, info)
        authority = info?.authority.orEmpty()
        Log.e("TAG", "attachInfo: " + authority)
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val tableName = getTableName(uri)
        Log.e("TAG", "tableName: $tableName")

        val cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder)
        return cursor
    }

    override fun getType(uri: Uri): String? {

        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    private fun getTableName(uri: Uri): String? = uri.pathSegments.firstOrNull()

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}