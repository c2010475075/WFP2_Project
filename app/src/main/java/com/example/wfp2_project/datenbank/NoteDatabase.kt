package com.example.wfp2_project.datenbank

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [NoteEntity::class]
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val dao: NoteDao

    companion object {
        const val name = "note_db"
    }
}