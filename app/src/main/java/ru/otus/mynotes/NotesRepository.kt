package ru.otus.mynotes

import kotlinx.coroutines.flow.Flow
import ru.otus.mynotes.database.NotesDao
import java.util.Date

object NotesRepository {

    private val storage: NotesDao = NotesApplication.db.notesDao()

    fun getAllNotes(): Flow<List<Note>> = storage.getAll()

    suspend fun create(title: String, note: String) {
        val note = Note(
            id = 0,
            title = title,
            text = note,
            date = Date()
        )
        storage.create(note)
    }

    suspend fun get(id: Long): Note? = storage.getNoteById(id)

    suspend fun update(note: Note) {
        storage.update(note)
    }
}