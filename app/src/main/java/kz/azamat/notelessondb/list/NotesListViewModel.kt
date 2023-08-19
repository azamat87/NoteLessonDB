package kz.azamat.notelessondb.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kz.azamat.notelessondb.Note
import kz.azamat.notelessondb.NotesRepository
import kz.azamat.notelessondb.NotesSQLiteRepository

class NotesListViewModel(private val repository: NotesSQLiteRepository = NotesSQLiteRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow<List<Note>>(emptyList())
    val viewState: Flow<List<Note>> get() = _viewState

    init {
        viewModelScope.launch {
            val notes = repository.getAllNotes()
            _viewState.value = notes
        }
    }
}