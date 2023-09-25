package com.example.sem7all

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class AirplaneModeChangeBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state",false)?: return

        if(isAirplaneModeEnabled){
            Toast.makeText(context,"Airplane Mode Enabled", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(context,"Airplane Mode Disabled", Toast.LENGTH_LONG).show()
        }
    }
}

class U26AirplaneModeChange : AppCompatActivity() {

    private lateinit var airplaneReceiver : AirplaneModeChangeBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.u26_airplane_mode_change)

        airplaneReceiver = AirplaneModeChangeBroadcastReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(airplaneReceiver,it)
        }

//        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
//        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
//        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(airplaneReceiver)
    }
}