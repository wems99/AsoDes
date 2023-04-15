package com.example.asodes.infrastructure.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

class TimestampDateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}