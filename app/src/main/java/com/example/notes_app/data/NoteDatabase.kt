package com.example.notes_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.notes_app.model.Note
import com.example.notes_app.utils.DateConverter
import com.example.notes_app.utils.UUIDcoverter

@Database(entities = [Note::class],version = 6)
@TypeConverters(UUIDcoverter::class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao():NoteDatabaseDao
}
