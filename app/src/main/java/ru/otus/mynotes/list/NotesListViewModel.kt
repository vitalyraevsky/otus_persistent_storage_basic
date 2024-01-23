package ru.otus.mynotes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.otus.mynotes.Note
import ru.otus.mynotes.MemNotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.otus.mynotes.NotesRepository
import ru.otus.mynotes.preferences.PreferenceProvider

class NotesListViewModel(
    private val repository: NotesRepository = NotesRepository,
    private val preferences: PreferenceProvider = PreferenceProvider
) : ViewModel() {
    private val _viewState : MutableStateFlow<NotesListViewState>

    val viewState: Flow<NotesListViewState> get() = _viewState

    init {
        val columnCount = preferences.getColumnCount()
        val state = NotesListViewState(
            notes = emptyList(),
            columnCount = columnCount
        )

        _viewState = MutableStateFlow(state)
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            val notes = repository.getAllNotes().collect {
                _viewState.value = _viewState.value.copy(notes = it)
            }
        }
    }

    fun onColumnCountChanged(count: Int) {
        _viewState.value = _viewState.value.copy(columnCount = count)
        preferences.setColumnCount(count)
    }

    fun onViewResumed() {
        loadNotes()
    }
}