package com.manuni.apiwithretrofitcrud.components.bound_service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MyServiceBound : Service() {

    /**
     * Service lifecycle override functions
     */
    private val TAG = "MyService"

    private var isServiceRunning = false
    private var isPrint = false

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    //onStartCommand is called when only service is called to start
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
                var myRandomNumber = 0
                if (isPrint){
                    myRandomNumber = Random.nextInt(1000,10000)
                }
                Log.d(TAG, "onStartCommand: Running tasks: $myRandomNumber")
                delay(1000)
            }
        }


        return Service.START_NOT_STICKY//it will not automatically start after for any reason stopped
        //return Service.START_STICKY//it will automatically start when service is stopped for any reason,,,it will tell the operating system to do that
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: ")
        return MyBinder()
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
        //if we don't want to stop self we can commented it out while required
        stopSelf()

    }

    inner class MyBinder: Binder(){
        fun getService():MyServiceBound{
            return this@MyServiceBound
        }
    }

    fun printRandomNumber(isPrint:Boolean){
        this.isPrint = isPrint
    }
}