package com.example.timetracker.main.Service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.timetracker.R

class NotificationService : Service() {

    private val binder = LocalBinder()
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): NotificationService = this@NotificationService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create and show the notification

        // Replace 1 with a unique notification ID
        // Return START_STICKY to indicate that the service should be restarted if it's killed
        return START_STICKY
    }


    fun createNotification(title: String, content: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, "reminder_channel")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.icons_warning) // Replace with your notification icon
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
    }

    fun showNotification(notificationId: Int, notificationBuilder: NotificationCompat.Builder) {
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
