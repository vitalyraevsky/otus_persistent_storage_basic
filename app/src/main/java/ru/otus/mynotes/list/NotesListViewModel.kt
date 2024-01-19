package ru.otus.mynotes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.otus.mynotes.Note
import ru.otus.mynotes.MemNotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.otus.mynotes.NotesRepository

class NotesListViewModel(
    private val repository: NotesRepository = NotesRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow<List<Note>>(emptyList())

    val viewState: Flow<List<Note>> get() = _viewState

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            val notes = repository.getAllNotes().collect {
                _viewState.value = it
            }
        }
    }

    fun onViewResumed() {
        loadNotes()
    }
}