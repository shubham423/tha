package com.example.tha.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tha.data.models.Note


@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(note: Note)

    @Delete
    suspend fun deleteItem(note: Note)

    @Update
    suspend fun updateData(note: Note)


}