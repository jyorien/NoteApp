package com.example.noteapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.noteapp.room.NoteEntity
import com.example.noteapp.room.RoomDb
import kotlin.concurrent.thread

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private var database: RoomDb
    private val context = getApplication<Application>()

    init {
    database = RoomDb.getDatabase(context)
    }
    val noteList = database.roomDao().getNotes()

    fun addNote(title: String, desc: String) {
        val note = NoteEntity(uid = null,noteTitle = title, noteDesc = desc)
        thread {
            database.roomDao().addNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        Log.i("hello",note.toString())
        thread {
            database.roomDao().deleteNote(note)
        }
    }
}