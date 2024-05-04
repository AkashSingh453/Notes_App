package com.example.notes_app.utils

import androidx.room.TypeConverter
import java.util.UUID

class UUIDcoverter {
    @TypeConverter
    fun fromUUId(uuid: UUID) : String?{
        return uuid.toString()
    }
    @TypeConverter
    fun uuidFromString(string: String?) : UUID?{
        return UUID.fromString(string)
    }
}