package com.manuni.apiwithretrofitcrud.components.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver:BroadcastReceiver() {
    private val TAG = "MyBroadcastReceiver"

    //this onReceive method will take the intent action from the os with the help of intent
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {
            when(it){
                Intent.ACTION_AIRPLANE_MODE_CHANGED->{
                    val isAirplaneMode: Boolean = isAirPlaneModeOn(context)
                    Log.d(TAG, "onReceive: $isAirplaneMode")
                    Toast.makeText(context,"$isAirplaneMode",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //this method will ensure that the system has the airplane mode is on state or off
    private fun isAirPlaneModeOn(context: Context?):Boolean{
        return Settings.System.getInt(context?.contentResolver,Settings.System.AIRPLANE_MODE_ON,0) !=0
    }
}