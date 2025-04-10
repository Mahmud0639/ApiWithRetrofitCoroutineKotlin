package com.manuni.apiwithretrofitcrud.components.foreground_service

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manuni.apiwithretrofitcrud.databinding.ActivityServiceNotificationBinding

class NotificationService : AppCompatActivity() {
    private lateinit var binding: ActivityServiceNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}