package com.example.notes_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "notes_tbl")
data class Note(
    @PrimaryKey
    val id: UUID,

    @ColumnInfo(name = "note_title")
    val title: String,


    @ColumnInfo(name = "note_entry_date")
    val entrydate: String = LocalDate.now().toString()
)


