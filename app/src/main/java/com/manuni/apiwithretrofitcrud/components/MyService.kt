package com.manuni.apiwithretrofitcrud.components

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

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
        Log.d(TAG, "onStartCommand: ")
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


        return Service.START_NOT_STICKY//it will not automatically start after for any reason stopped
        //return Service.START_STICKY//it will automatically start when service is topped for any reason,,,it will tell the operating system to do that
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