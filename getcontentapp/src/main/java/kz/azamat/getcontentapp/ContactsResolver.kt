package kz.azamat.getcontentapp

import android.content.Context
import android.net.Uri
import android.provider.BaseColumns
import android.provider.ContactsContract

class ContactsResolver(context: Context) {

    private val resolver = context.contentResolver

    fun getNotes(): List<Note> {
        val result = mutableListOf<Note>()

        val projection = arrayOf(
            BaseColumns._ID,
            "title",
            "note"
        )

        val uri = Uri.parse("content://kz.azamat.notelessondb.NoteLessonDB/notes")

        val cursor = resolver.query(uri,
            projection,
            null,
            null,
            null)
            ?: return emptyList()

        if (!cursor.moveToFirst()) return emptyList()

        do {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val note = cursor.getString(cursor.getColumnIndexOrThrow("note"))
            result.add(Note(id = id, title = title, note = note))
        } while (cursor.moveToNext())
        cursor.close()
        return result
    }

    fun getContacts(): List<Contact> {
        val result = mutableListOf<Contact>()

        val projection = arrayOf(
            "id",
            "title"
        )

//        content://com.contactapp.provider.Contac../contact/1
        val uri = Uri.parse("content://kz.azamat.notelessondb.NoteLessonDB/")
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                null)
                ?: return emptyList()

        if (!cursor.moveToFirst()) return emptyList()

        do {
            val contactId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
            result.add(Contact(id = contactId, name = name))
        } while (cursor.moveToNext())
        cursor.close()
        return result
    }


}

data class Contact(val id: Long, val name: String)

data class Note(val id: Long, val title: String, val note: String)