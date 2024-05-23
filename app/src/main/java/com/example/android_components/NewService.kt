package com.example.android_components

import android.app.Service
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.IBinder

class NewService : Service() {
    private lateinit var ringtone: Ringtone
    

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone.play()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ringtone.isPlaying) {
            ringtone.stop()
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
