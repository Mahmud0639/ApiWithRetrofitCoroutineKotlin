package com.manuni.apiwithretrofitcrud.components.broadcast_receiver.custom

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manuni.apiwithretrofitcrud.databinding.ActivityCustomBroadcastReceiverBinding

class CustomBroadcastReceiver : AppCompatActivity() {
    private lateinit var binding: ActivityCustomBroadcastReceiverBinding

    val TAG = "CustomBroadcastReceiver"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomBroadcastReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendBroadCast.setOnClickListener {
            broadCastIntent()
        }

    }



    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.manuni.apiwithretrofitcrud.components.broadcast_receiver.custom")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(myReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            // For Android 12 and below
            registerReceiver(myReceiver, filter, null, null)
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            unregisterReceiver(myReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun broadCastIntent() {
        val intent = Intent().apply {
            action = "com.manuni.apiwithretrofitcrud.components.broadcast_receiver.custom"
            putExtra("MY_DATA", "This is my custom broadcast intent data")
        }
        sendBroadcast(intent)
    }

    //we can also make a class of it like we used in the previous broadcast receiver example
    //it is the same approach but we use here another way in the same activity not making any custom class

    private val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.action?.let {
                if (it == "com.manuni.apiwithretrofitcrud.components.broadcast_receiver.custom") {
                    Log.d(TAG, "onReceive: ${intent.getStringExtra("MY_DATA")}")
                    Toast.makeText(
                        context,
                        "Data is: ${intent.getStringExtra("MY_DATA")}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}