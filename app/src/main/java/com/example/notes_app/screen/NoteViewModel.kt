package com.example.notes_app.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Index
import com.example.notes_app.Repository.NoteRepository
import com.example.notes_app.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged()
                .collect{listOfNotes ->
                    if (listOfNotes.isEmpty()){
                        Log.d("mkcd",":Empty List")
                    }else{
                        _noteList.value = listOfNotes
                    }

                }
        }
    }
     fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }
     fun removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }
    var note: Note? = null


    suspend fun fetchNoteById(noteId: UUID) {
        note = repository.getnotebyID(noteId)
    }

}
