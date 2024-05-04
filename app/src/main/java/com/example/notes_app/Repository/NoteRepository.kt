package com.example.notes_app.Repository

import androidx.room.Index
import com.example.notes_app.data.NoteDatabaseDao
import com.example.notes_app.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {

    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note)
    suspend fun updateNote(note: Note)=noteDatabaseDao.update(note)
    suspend fun deleteNote(note: Note)=noteDatabaseDao.deleteNote(note)
    suspend fun deleteAllNotes()=noteDatabaseDao.deleteAll()
    suspend fun getAllNotes(): Flow<List<Note>> = noteDatabaseDao.getNotes().flowOn(Dispatchers.IO)
        .conflate()
    suspend fun  getnotebyID(noteId: UUID) :Note = noteDatabaseDao.getNotebyId(noteId)
}



