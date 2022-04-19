package com.me.android_service.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.me.android_service.services.MyForegroundService

class BootBroadcastReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)){
            context?.let {
                val foregroundIntent = Intent(it, MyForegroundService::class.java)
                it.startForegroundService(foregroundIntent)
            }
        }
    }
}