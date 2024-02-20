package com.example.wfp2_project

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.wfp2_project.notification.NotificationConstants.CHANNEL_ID
import com.example.wfp2_project.notification.NotificationConstants.channel_description
import com.example.wfp2_project.notification.NotificationConstants.channel_name
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channel_name, importance).apply {
                description = channel_description
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

