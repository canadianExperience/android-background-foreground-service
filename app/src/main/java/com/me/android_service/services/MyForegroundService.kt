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
import com.me.android_service.CHANNEL_ID
import com.me.android_service.MainActivity
import com.me.android_service.R

@RequiresApi(Build.VERSION_CODES.O)
class MyForegroundService: Service() {

    private lateinit var myThread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        myThread = Thread {
            try {
                while (!Thread.currentThread().isInterrupted) {
                    Log.e("SERVICE", "Foreground(Notifications) Service is running...")
                    sendNotifications()
                    Thread.sleep(5000)
                }
            } catch (e: InterruptedException){
                e.printStackTrace()
            }
        }
        myThread.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        myThread.interrupt()
        stopSelf()
    }

    private fun sendNotifications(){
        val id = (1..1001).random()
        val intent = Intent(this, MainActivity::class.java)
        val flags = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent = PendingIntent.getActivity(
            this,
            id,
            intent,
            flags
        )

        val builder = Notification.Builder(this, CHANNEL_ID).apply {
            setContentText("Foreground Service is running every 5 sec")
            setContentTitle("Notifications enabled with id: $id")
            setSmallIcon(R.drawable.ic_notifications_active)
            setContentIntent(pendingIntent)
            setCategory(NotificationCompat.CATEGORY_MESSAGE)
            setAutoCancel(true)
        }

        startForeground(id, builder.build())
    }
}