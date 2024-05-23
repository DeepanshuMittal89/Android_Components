package com.example.android_components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.Toast

class BatteryChangerReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val level : Int? = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL,0)
        if (level != null) {
            if(level<15){
                Toast.makeText(context,"Low Battery !!", Toast.LENGTH_LONG).show()
            }
        }
    }
}