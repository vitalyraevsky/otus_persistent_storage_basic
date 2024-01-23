package ru.otus.mynotes.preferences

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import ru.otus.mynotes.NotesApplication

private const val DATASTORE_FILE = "app_datastore"
private const val KEY_COLUMN_COUNT = "column_count"
private val keyColumnCount = intPreferencesKey(KEY_COLUMN_COUNT)
private const val DEF_COLUMN_COUNT = 2

object PreferenceProvider {

    private val dataStore = NotesApplication.context.settingsDataStore

    suspend fun setColumnCount(count: Int) {
        dataStore.edit { preferences ->
            preferences[keyColumnCount] = count
        }
    }

    fun getColumnCount(): Flow<Int> = dataStore.data.mapNotNull { preferences ->
        preferences[keyColumnCount]
    }

}

private val Context.settingsDataStore by preferencesDataStore(
    name = DATASTORE_FILE,
    produceMigrations = { context -> listOf(createMigration(context)) }
)

fun createMigration(context: Context): DataMigration<Preferences> {
    return SharedPreferencesMigration(
        context = context,
        sharedPreferencesName = SHARED_PREF_FILE,
        keysToMigrate = setOf(KEY_COLUMN_COUNT)
    )
}
