package kz.azamat.notelessondb

import android.app.Application
import android.content.Context

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
            private set
    }
}