package com.manuni.apiwithretrofitcrud.collapsing_toolbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityCollapsingToolbarBinding

class CollapsingToolbarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollapsingToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollapsingToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}