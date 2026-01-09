package com.example.myapplication.presentation.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.example.myapplication.R
import com.google.firebase.messaging.RemoteMessage
import kotlinx.datetime.Clock
import androidx.core.net.toUri

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {

    }

    override fun onMessageReceived(message: RemoteMessage) {
        val pushType = message.data["push_type"].orEmpty()
        val title = message.data["title"].orEmpty()
        val body = message.data["body"].orEmpty()

        createNotificationChannel()
        showNotification(
            title = title,
            text = body,
            pushType = pushType,
        )
    }


    private fun showNotification(title: String, text: String, pushType: String) {
        // Match exact deep link format from nav_graph
        val deepLinkUri = when (pushType.uppercase()) {
            "USERS" -> "MyApplication//::register"
            "HOME" -> "MyApplication//::home"
            else -> "MyApplication//::login"
        }

        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri.toUri()).apply {
            setPackage(applicationContext.packageName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, "11111")
            .setSmallIcon(R.drawable.user)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@with
            }
            notify(Clock.System.now().toEpochMilliseconds().toInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.email)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("11111", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

