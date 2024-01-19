package ru.otus.mynotes

import java.util.*

object NotesRepository {
    private val notes: MutableList<Note> = mutableListOf()

    fun getAllNotes(): List<Note> = notes

    fun create(title: String, note: String) {
        if (title.isBlank() && note.isBlank()) return

        val maxId = notes.maxByOrNull { it.id }?.id ?: 0
        val newNote = Note(
            id = maxId + 1,
            title = title,
            text = note,
            date = Date()
        )
        notes.add(newNote)
    }

    fun update(note: Note) {
        notes.replaceAll { if (it.id == note.id) note else it }
    }

    fun get(id: Long): Note? = notes.firstOrNull { it.id == id }
}