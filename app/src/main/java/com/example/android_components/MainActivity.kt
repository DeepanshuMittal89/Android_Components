package com.example.android_components

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import com.example.android_components.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var aeroplaneModeChangeReceiver: AeroplaneModeChangerReciever
    private lateinit var batteryChangeReceiver: BatteryChangerReciever

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notification"
    private val description = "Test Notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startServiceBtn.setOnClickListener{
            startService(Intent(this, NewService::class.java))
        }
        binding.stopServiceBtn.setOnClickListener{
            stopService(Intent(this, NewService::class.java))
        }
        binding.notificationBtn.setOnClickListener{
            createNotification()
        }

        aeroplaneModeChangeReceiver = AeroplaneModeChangerReciever()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(aeroplaneModeChangeReceiver, it)
        }

        batteryChangeReceiver = BatteryChangerReciever()
        IntentFilter(Intent.ACTION_BATTERY_LOW).also {
            registerReceiver(batteryChangeReceiver, it)
        }
    }

    private fun createNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, myNotifications::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews(packageName, R.layout.activity_my_notifications)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_notification_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_notification_foreground))
                .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(this)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_notification_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_notification_foreground))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(aeroplaneModeChangeReceiver)
        unregisterReceiver(batteryChangeReceiver)
    }
}
