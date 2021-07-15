package com.example.noteapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int?,
    @ColumnInfo
    val noteTitle: String,
    @ColumnInfo
    val noteDesc: String
)