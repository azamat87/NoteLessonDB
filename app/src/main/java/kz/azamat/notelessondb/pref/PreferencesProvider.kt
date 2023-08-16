package kz.azamat.notelessondb.pref

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kz.azamat.notelessondb.NoteApplication

private const val KEY_NOTE_COUNT = "key_note_count"
private val keyNoteCount = intPreferencesKey(KEY_NOTE_COUNT)

object PreferencesProvider {

    private val dataStore = NoteApplication.context.settingsDataStore

    suspend fun setNoteCount(count: Int) {
        dataStore.edit { settings ->
            settings[keyNoteCount] = count
        }
    }

    fun getNoteCount(): Flow<Int> = dataStore.data.mapNotNull { values: Preferences ->
        values[keyNoteCount]
    }

}

private val Context.settingsDataStore by preferencesDataStore(
    name = "setting_prefs",
    produceMigrations = { context -> listOf(createMigration(context)) }
)

private fun createMigration(context: Context) = SharedPreferencesMigration(
        context = context,
        "note_app_pref",
        keysToMigrate = setOf(KEY_NOTE_COUNT)
    )


//object PreferencesProvider {
//
//    private const val KEY_NOTE_COUNT = "key_note_count"
//
//    private val preferences = NoteApplication.context.getSharedPreferences("note_app_pref", Context.MODE_PRIVATE)
//
//    fun setNoteCount(count: Int) {
//        val editor = preferences.edit()
//        editor.putInt(KEY_NOTE_COUNT, count)
//            .apply()
//    }
//
//    fun getNoteCount(): Int {
//        return preferences.getInt(KEY_NOTE_COUNT, 0)
//    }
//
//}