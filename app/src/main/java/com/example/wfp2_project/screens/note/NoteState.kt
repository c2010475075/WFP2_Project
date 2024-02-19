package com.example.wfp2_project.screens.note

data class NoteState(
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val date: Long? = null // Speichert das Datum als Zeitstempel
)
