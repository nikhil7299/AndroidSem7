package com.example.sem7all

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView

class BoundService: Service(){
    private val LOG_TAG = "BoundService"
    private val myBinder: IBinder = MyBinder()
    private var chronometer: Chronometer? = null
    override fun onCreate() {
        super.onCreate()
        Log.v(LOG_TAG, "in onCreate")
        chronometer = Chronometer(this)
        chronometer!!.setBase(SystemClock.elapsedRealtime())
        chronometer!!.start()
    }
    override fun onBind(p0: Intent?): IBinder? {
        return  myBinder
    }
    override fun onRebind(intent: Intent?) {
        Log.v(LOG_TAG, "In onRebind")
        super.onRebind(intent)
    }
    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(LOG_TAG, "In onUnbind")
        return true
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.v(LOG_TAG, "In onDestroy")
        chronometer!!.stop()
    }
    fun getTimestamp(): String? {
        val elapsedMillis = (SystemClock.elapsedRealtime() - chronometer!!.base)
        val hours = (elapsedMillis / 3600000).toInt()
        val minutes = (elapsedMillis - hours * 3600000).toInt() / 60000
        val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000).
        toInt() / 1000
        val millis = (elapsedMillis - hours * 3600000 - minutes * 60000 -
                seconds * 1000).toInt()
        return "$hours:$minutes:$seconds:$millis"
    }

    inner  class MyBinder: Binder()
    {
        fun getService():BoundService {
            return this@BoundService
        }
    }
}

class U24BoundService : AppCompatActivity() {
    var boundService: BoundService? = null
    var myServiceBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u24_bound_service)

        val boundServiceTimeStampText = findViewById<TextView>(R.id.boundServiceTimeStampText)
        val boundServicePrintTimestampBtn: Button = findViewById(R.id.boundServicePrintTimestampBtn)
        val boundServiceStopBtn: Button = findViewById(R.id.boundServiceStopBtn)

        boundServicePrintTimestampBtn.setOnClickListener {
            if (myServiceBound) {
                boundServiceTimeStampText.text = boundService!!.getTimestamp()
            }
        }
        boundServiceStopBtn.setOnClickListener {
            if (myServiceBound) {
                unbindService(myServiceConnection)
                myServiceBound = false
            }
            val intent = Intent(
                this@U24BoundService, BoundService::class.java)
            stopService(intent)

        }

    }
    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        startService(intent)

        bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE)
    }
    override fun onStop() {
        super.onStop()
        if (myServiceBound) {
            unbindService(myServiceConnection)
            myServiceBound = false
        }
    }
    val myServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            myServiceBound = false
        }
        override fun onServiceConnected(name: ComponentName, service: IBinder)
        {
            val myBinder: BoundService.MyBinder = service as
                    BoundService.MyBinder
            boundService = myBinder.getService()
            myServiceBound = true
        }
    }
}