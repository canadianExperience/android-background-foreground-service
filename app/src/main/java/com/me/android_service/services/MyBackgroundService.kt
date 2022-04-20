package com.me.android_service.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyBackgroundService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       Thread {
           try {
               while (!Thread.currentThread().isInterrupted) {
                   Log.e("SERVICE", "Background Service is running...")
                   Thread.sleep(5000)
               }
           } catch (e: InterruptedException){
               e.printStackTrace()
           }
       }.start()

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}