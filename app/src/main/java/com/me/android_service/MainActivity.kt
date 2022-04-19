package com.me.android_service

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.me.android_service.services.MyBackgroundService
import com.me.android_service.services.MyForegroundService

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backgroundIntent = Intent(this, MyBackgroundService::class.java)
        val foregroundIntent = Intent(this, MyForegroundService::class.java)

        startService(backgroundIntent)
        startForegroundService(foregroundIntent)
    }
}