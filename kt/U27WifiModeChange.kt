package com.example.sem7all

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast

class U27WifiModeChange : AppCompatActivity() {
    lateinit var wifiSwitch : Switch
    lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u27_wifi_mode_change)

        wifiSwitch = findViewById(R.id.wifiSwitch)
        wifiManager= applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        wifiSwitch.setOnCheckedChangeListener{
                _,isChecked ->
            if(isChecked){
                wifiManager.isWifiEnabled = true
                wifiSwitch.text = " WIFI IS ON"
            }
            else{
                wifiManager.isWifiEnabled = false
                wifiSwitch.text = " WIFI IS OFF"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }

    private  val wifiStateReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN)){
                WifiManager.WIFI_STATE_ENABLED->{
                    wifiSwitch.isChecked  = true
                    wifiSwitch.text = "WIFI IS ON"
                    Toast.makeText(this@U27WifiModeChange,"WIFI IS ON", Toast.LENGTH_LONG).show()
                }
                WifiManager.WIFI_STATE_DISABLED->{
                    wifiSwitch.isChecked  = false
                    wifiSwitch.text = "WIFI IS OFF"
                    Toast.makeText(this@U27WifiModeChange,"WIFI IS OFF", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}