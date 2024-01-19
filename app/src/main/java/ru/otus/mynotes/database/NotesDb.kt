package ru.otus.mynotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.otus.mynotes.Note

@Database(version = 1, entities = [Note::class])
@TypeConverters(DataConvertor::class)
abstract class NotesDb : RoomDatabase() {

    abstract fun notesDao(): NotesDao
}