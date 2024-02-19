package com.example.wfp2_project.datenbank

import com.example.wfp2_project.common.Note


fun NoteEntity.asExternalModel(): Note = Note(
    id, title, content, date
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id, title, content, date
)