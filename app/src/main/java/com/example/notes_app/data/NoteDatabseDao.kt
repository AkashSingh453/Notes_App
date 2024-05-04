package com.example.notes_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notes_app.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NoteDatabaseDao {
    @Query("SELECT * from notes_tbl")
    fun getNotes():
            Flow<List<Note>>

    @Query("SELECT * from notes_tbl where id =:noteid")
    suspend fun getNotebyId(noteid:UUID) : Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("DELETE from notes_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Note)
}

