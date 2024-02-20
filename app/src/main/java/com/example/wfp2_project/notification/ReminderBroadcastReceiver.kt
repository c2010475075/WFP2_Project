package com.example.wfp2_project.notification
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.wfp2_project.MainActivity
import com.example.wfp2_project.R
import com.example.wfp2_project.notification.NotificationConstants.CHANNEL_ID

class ReminderBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ReminderBroadcastReceiver", "onReceive called")
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
        val notificationId = intent.getIntExtra("noteId", 0)
        val noteTitle = intent.getStringExtra("noteTitle") ?: "Erinnerung"
        val notification = getNotification(context, notificationId, noteTitle)
        notificationManager.notify(notificationId, notification)
    }

    private fun getNotification(context: Context, notificationId: Int, noteTitle: String): Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(noteTitle) // Verwende den extrahierten Titel
            .setContentText("Du hast eine anstehende Aufgabe.")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

}
