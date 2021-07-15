package com.example.noteapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {

    @Insert
    fun addNote(note: NoteEntity)

    @Query("SELECT * FROM NoteEntity")
    fun getNotes(): LiveData<List<NoteEntity>>

    @Delete
    fun deleteNote(note: NoteEntity)
}