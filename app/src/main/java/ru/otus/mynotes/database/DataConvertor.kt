package ru.otus.mynotes.database

import androidx.room.TypeConverter
import java.util.Date

class DataConvertor {

    @TypeConverter
    fun dateToLong(date: Date) : Long = date.time

    @TypeConverter
    fun longToDate(time: Long) : Date = Date(time)
}