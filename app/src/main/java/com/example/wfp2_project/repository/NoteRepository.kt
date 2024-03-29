package com.example.wfp2_project.repository


import com.example.wfp2_project.common.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun scheduleNotificationForNote(noteId: Int, noteTitle: String)


}

