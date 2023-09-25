package com.example.sem7all

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.Button

class UnboundService : Service() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        mediaPlayer.isLooping =true
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

class U23UnboundService : AppCompatActivity() {
    private lateinit var unboundStartBtn: Button
    private lateinit var unboundStopBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u23_unbound_service)

        unboundStartBtn = findViewById(R.id.unboundStartBtn)
        unboundStopBtn = findViewById(R.id.unboundStopBtn)
        unboundStartBtn.setOnClickListener{

            startService(Intent(this@U23UnboundService, UnboundService::class.java))
        }
        unboundStopBtn.setOnClickListener{
            stopService(Intent(this@U23UnboundService, UnboundService::class.java))
        }
    }
}