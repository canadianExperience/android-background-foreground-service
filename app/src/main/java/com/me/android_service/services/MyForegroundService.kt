package com.me.android_service.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.me.android_service.CHANNEL_ID
import com.me.android_service.MainActivity
import com.me.android_service.R
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
class MyForegroundService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            while (true) {
                Log.e("SERVICE", "Foreground(Notifications) Service is running...")
                sendNotifications()
                Thread.sleep(5000)
            }
        }.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    private fun sendNotifications(){
        val id = Random.nextInt(1001)
        val intent = Intent(this, MainActivity::class.java)
        val flags = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent = PendingIntent.getActivity(
            this,
            id,
            intent,
            flags
        )

        val actionIntent = Intent(this, MainActivity::class.java).apply {
            action = "Stop"
            putExtra("stop_notifications", true)
        }
        val actionFlags = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
        val actionPendingIntent = PendingIntent.getActivity(
            this,
            id,
            actionIntent,
            actionFlags
        )

        val builder = Notification.Builder(this, CHANNEL_ID).apply {
            setContentText("Foreground Service is running every 5 sec")
            setContentTitle("Notifications enabled with id: $id")
            setSmallIcon(R.drawable.ic_notifications_active)
            setContentIntent(pendingIntent)
            setCategory(NotificationCompat.CATEGORY_MESSAGE)
            setAutoCancel(true)
            addAction(R.drawable.ic_notifications_off, "Stop", actionPendingIntent)
        }
        with(NotificationManagerCompat.from(this)) {
            notify(id, builder.build())
        }
    }
}