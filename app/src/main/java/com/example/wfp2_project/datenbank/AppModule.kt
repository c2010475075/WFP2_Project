package com.example.wfp2_project.datenbank

import android.content.Context
import androidx.room.Room
import com.example.wfp2_project.repository.NoteRepository
import com.example.wfp2_project.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.name
        ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase, @ApplicationContext context: Context): NoteRepository =
        NoteRepositoryImpl(dao = database.dao, context = context )
}