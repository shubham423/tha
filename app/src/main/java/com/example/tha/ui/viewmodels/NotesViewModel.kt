package com.example.tha.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tha.data.models.Note
import com.example.tha.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor (@ApplicationContext application: Context, private val repository: NotesRepository) : AndroidViewModel(application as Application) {


    val getAllData: LiveData<List<Note>> = repository.getAllData


    fun insertData(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(note)
        }
    }

    fun updateData(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(note)
        }
    }

    fun deleteItem(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(note)
        }
    }

}