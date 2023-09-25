package com.example.sem7all

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class ForegroundService: Service()
{
    private val CHANNEL_ID = "ForegroundService Kotlin"
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        val input = intent?.getStringExtra("foregroundService")
        createNotificationChannel()
        val notificationIntent = Intent(this,
            U25ForegroundService::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}

class U25ForegroundService : AppCompatActivity() {
    private lateinit var foregroundStartBtn: Button
    private lateinit var foregroundStopBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u25_foreground_service)

        foregroundStartBtn=findViewById(R.id.foregroundStartBtn)
        foregroundStopBtn=findViewById(R.id.foregroundStopBtn)

        foregroundStartBtn.setOnClickListener {
            val startIntent = Intent(this, ForegroundService::class.java)
            startIntent.putExtra("foregroundService", "Foreground Service is running...")
            ContextCompat.startForegroundService(this, startIntent)
        }
        foregroundStopBtn.setOnClickListener {
            val stopIntent = Intent(this, ForegroundService::class.java)
            stopService(stopIntent)
        }
    }
}