package com.example.android_components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AeroplaneModeChangerReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAeroplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
        if(isAeroplaneModeEnabled){
            Toast.makeText(context,"Aeroplane Mode Enabled !!", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(context,"Aeroplane Mode is Disabled!!", Toast.LENGTH_LONG).show()
        }
    }
}