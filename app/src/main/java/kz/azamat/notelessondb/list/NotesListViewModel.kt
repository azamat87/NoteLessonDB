package kz.azamat.notelessondb.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kz.azamat.notelessondb.Note
import kz.azamat.notelessondb.NotesRepository

class NotesListViewModel(private val repository: NotesRepository = NotesRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow<List<Note>>(emptyList())
    val viewState: Flow<List<Note>> get() = _viewState

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { notes ->
                _viewState.value = notes
            }
        }
    }
}