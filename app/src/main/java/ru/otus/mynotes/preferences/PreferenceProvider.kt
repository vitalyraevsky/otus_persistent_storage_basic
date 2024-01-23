package ru.otus.mynotes.preferences

import android.content.Context
import ru.otus.mynotes.NotesApplication

private const val SHARED_PREF_FILE = "app_prefs"
private const val KEY_COLUMN_COUNT = "column_count"
private const val DEF_COLUMN_COUNT = 2

object PreferenceProvider {

    private val preferences = NotesApplication.context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

    fun setColumnCount(count: Int) {
        preferences.edit().apply {
            putInt(KEY_COLUMN_COUNT, count)
            apply()
        }
    }

    fun getColumnCount() = preferences.getInt(KEY_COLUMN_COUNT, DEF_COLUMN_COUNT)
}