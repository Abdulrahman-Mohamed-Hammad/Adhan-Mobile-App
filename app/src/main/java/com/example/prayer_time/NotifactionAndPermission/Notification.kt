package com.example.prayer_time.NotifactionAndPermission

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Notification.DEFAULT_SOUND
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.prayer_time.MainActivity
import com.example.prayer_time.R
import com.example.prayer_time.SCRREN.StopPryerNow

@Suppress("DEPRECATION")
class Notification {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingPermission")
    fun notification(
        ChannelId:String,name:String,
        context: Context,
        title: String,
        content: String,
        importanceHigh: Int,
        priorityHigh: Int,

        ) {
        val channelID = ChannelId
        var channel = NotificationChannel(
            channelID,
            name,
            importanceHigh
        ).apply { enableVibration(true);enableLights(true); }
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Create the PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // ---- Delete Intent + Inline Receiver ----
        val deleteAction = "com.example.NOTIFICATION_DISMISSED"
        val deleteIntent = Intent(deleteAction)
        val deletePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                StopPryerNow()
            }
        }, IntentFilter(deleteAction), Context.RECEIVER_NOT_EXPORTED)
        val builder = Notification.Builder(context, channelID).setContentText(content)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.frame_25))
            .setContentTitle(title).setSmallIcon(R.drawable._2_clear_night)
            .setFullScreenIntent(null, true).setDefaults(DEFAULT_SOUND)
            .setPriority(priorityHigh).setAutoCancel(false).setContentIntent(pendingIntent).setDeleteIntent(deletePendingIntent)
////      if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0, builder.build())

    }
}