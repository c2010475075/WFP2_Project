package com.example.wfp2_project.datenbank

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int?,
    val title: String,
    val content: String,
    val date: Long? = null
)
