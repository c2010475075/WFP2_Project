package com.example.wfp2_project.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.wfp2_project.common.Note
import com.example.wfp2_project.datenbank.NoteDao
import com.example.wfp2_project.datenbank.asExternalModel
import com.example.wfp2_project.datenbank.toEntity
import com.example.wfp2_project.notification.ReminderBroadcastReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val context: Context
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
            .map { notes ->
                notes.map {
                    it.asExternalModel()
                }
            }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.asExternalModel()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toEntity())
    }
    override suspend fun scheduleNotificationForNote(noteId: Int, noteTitle: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                // Berechtigung ist nicht erteilt
                // Hier könntest du eine Benachrichtigung anzeigen oder den Nutzer direkt zur Einstellungsseite führen
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
                return // Beende die Methode, da wir ohne Berechtigung nicht fortfahren können
            }
        }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 4)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        val alarmTimeAt5AM = calendar.timeInMillis

        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            putExtra("noteId", noteId)
            putExtra("noteTitle", noteTitle)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, noteId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        Log.d("AlarmManager", "Scheduling alarm at: $alarmTimeAt5AM")

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeAt5AM, pendingIntent)
    }
}
