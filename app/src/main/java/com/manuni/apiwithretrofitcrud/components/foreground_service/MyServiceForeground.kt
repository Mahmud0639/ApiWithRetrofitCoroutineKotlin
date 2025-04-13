package com.manuni.apiwithretrofitcrud.components.foreground_service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.manuni.apiwithretrofitcrud.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyServiceForeground : Service() {

    /**
     * Service lifecycle override functions
     */
    private val TAG = "MyService"

    var isServiceRunning = false

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isServiceRunning = true

        //startMyForegroundService()

        val name = intent?.getStringExtra("MY_NAME")?:""

        Log.d(TAG, "onStartCommand: ${name}")
        //long running operation(like: audio, video play, download, read-write file, converter like video to mp3 etc long running operation)
        var mJob: Job? = null
        mJob = CoroutineScope(Dispatchers.IO).launch {

            if (!isServiceRunning){//if service is not running it will stop the coroutine job
                mJob?.cancel()
            }

            while (isServiceRunning) {
                Log.d(TAG, "onStartCommand: Running tasks")
                delay(1000)
            }
        }

        startMyForegroundService()

        return Service.START_NOT_STICKY//it will not automatically start after for any reason stopped
        //return Service.START_STICKY//it will automatically start when service is topped for any reason,,,it will tell the operating system to do that
    }

    @SuppressLint("ForegroundServiceType")
    private fun startMyForegroundService(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "My_Channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId,"Foreground Service",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

            val notificationBuilder = NotificationCompat.Builder(this@MyServiceForeground,channelId).apply {
                setContentTitle("Foreground Service")
                setContentText("Foreground service is running")
                setSmallIcon(R.drawable.ic_add)
            }

            startForeground(99,notificationBuilder.build())
        }





    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: ")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind: ")
        super.onRebind(intent)

    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
        isServiceRunning = false

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d(TAG, "onTaskRemoved: ")
        super.onTaskRemoved(rootIntent)
        //when it removed from the history then it will stop self
        stopSelf()

    }
}