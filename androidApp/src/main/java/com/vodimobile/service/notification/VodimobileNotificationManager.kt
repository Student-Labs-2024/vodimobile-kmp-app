package com.vodimobile.service.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat

class VodimobileNotificationManager(val context: Context) {
    fun buildNotification(
        intent: Intent,
        title: String,
        body: String,
        @DrawableRes notifLogo: Int,
        @ColorInt color: Int
    ): Notification {

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(
            context,
            NotificationsIds.LocalNotification.NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(notifLogo)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setColor(color)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(body))
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)

        return notificationBuilder.build()
    }

    fun sendNotification(notification: Notification) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationsIds.LocalNotification.NOTIFICATION_CHANNEL_ID,
                "channel name",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notification)
    }
}