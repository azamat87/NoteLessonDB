package kz.azamat.getcontentapp

import android.content.ContentResolver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val contactsResolver by lazy { ContactsResolver(this.applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        findViewById<Button>(R.id.buttonGetData).setOnClickListener {
//            val contacts = contactsResolver.getContacts()
            val notes = contactsResolver.getNotes()

            var text = ""
//            for (contact in contacts) {
//                text += "ID = ${contact.id} Name = ${contact.name} \n"
//            }

            for (note in notes) {
                text += "ID = ${note.id} Title = ${note.title} \n Note = ${note.note} \n\n"
            }
            textView.text = text

        }

    }
}