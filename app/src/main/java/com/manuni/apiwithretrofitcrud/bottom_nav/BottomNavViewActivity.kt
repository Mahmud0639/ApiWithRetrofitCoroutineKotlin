package com.manuni.apiwithretrofitcrud.bottom_nav

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityBottomNavViewBinding

class BottomNavViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.search ->{
                    Toast.makeText(this,"Search clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.appsBottom ->{
                    Toast.makeText(this,"Apps clicked",Toast.LENGTH_SHORT).show()
                }
                //like this we can clicked on others
            }
            true
        }

    }
}