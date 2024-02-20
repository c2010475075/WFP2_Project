package com.example.wfp2_project.screens.note

sealed class NoteEvent() {
    data class TitleChange(val value: String): NoteEvent()
    data class ContentChange(val value: String): NoteEvent()
    data class DateChange(val date: Long?) : NoteEvent()
    object Save : NoteEvent()
    object NavigateBack : NoteEvent()
    object DeleteNote : NoteEvent()
}