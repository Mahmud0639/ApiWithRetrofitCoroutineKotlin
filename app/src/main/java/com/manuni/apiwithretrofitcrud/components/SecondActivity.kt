package com.manuni.apiwithretrofitcrud.components

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manuni.apiwithretrofitcrud.databinding.ActivitySecondBinding

class SecondActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startServiceBtn.setOnClickListener {
            val intent = Intent(this,MyService::class.java)
            startService(intent)
        }

        binding.stopServiceBtn.setOnClickListener {
            stopService(Intent(this,MyService::class.java))
        }

    }
}