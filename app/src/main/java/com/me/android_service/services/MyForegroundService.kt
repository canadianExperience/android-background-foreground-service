package com.me.android_service.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.me.android_service.CHANNEL_ID
import com.me.android_service.R
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
class MyForegroundService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            while (true) {
                Log.e("SERVICE", "Foreground(Notifications) Service is running...")
                sendNotifications()
                Thread.sleep(2000)
            }
        }.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun sendNotifications(){
        val id = Random.nextInt(1001)
        val builder = Notification.Builder(this, CHANNEL_ID)
            .setContentText("Foreground Service is running")
            .setContentTitle("Notifications enabled with id: $id")
            .setSmallIcon(R.drawable.ic_notifications_active)

        startForeground(id, builder.build())
    }
}