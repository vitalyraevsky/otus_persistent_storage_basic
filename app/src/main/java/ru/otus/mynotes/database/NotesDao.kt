package ru.otus.mynotes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.otus.mynotes.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM  Note")
    fun getAll() : Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")
    suspend fun getNoteById(id: Long) : Note?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(note: Note)

    @Update
    suspend fun update(note: Note)
}