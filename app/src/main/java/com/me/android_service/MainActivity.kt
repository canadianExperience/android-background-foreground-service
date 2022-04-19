package com.me.android_service

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.me.android_service.databinding.ActivityMainBinding
import com.me.android_service.services.MyBackgroundService
import com.me.android_service.services.MyForegroundService


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val backgroundIntent = Intent(this, MyBackgroundService::class.java)
        startService(backgroundIntent)

        val foregroundIntent = Intent(this, MyForegroundService::class.java)

        if(!hasNotificationServiceRunning()){
            startForegroundService(foregroundIntent)
        }

        binding.stopBtn.setOnClickListener {
            //Stop notifications
            stopService(foregroundIntent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun hasNotificationServiceRunning(): Boolean{
        //Better way to save boolean in dataStore prefs

        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (MyForegroundService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }
}