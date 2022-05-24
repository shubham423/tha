package com.example.tha.data.repository

import androidx.lifecycle.LiveData
import com.example.tha.data.local.NotesDao
import com.example.tha.data.models.Note
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {

    val getAllData: LiveData<List<Note>> = notesDao.getAllData()

    suspend fun insertData(Note: Note){
        notesDao.insertData(Note)
    }

    suspend fun updateData(Note: Note){
        notesDao.updateData(Note)
    }

    suspend fun deleteItem(note: Note){
        notesDao.deleteItem(note)
    }

}