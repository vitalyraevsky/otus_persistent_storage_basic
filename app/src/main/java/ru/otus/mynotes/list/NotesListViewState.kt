package ru.otus.mynotes.list

import ru.otus.mynotes.Note

data class NotesListViewState(
    val notes: List<Note>,
    val columnCount: Int
)
