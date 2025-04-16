package com.manuni.apiwithretrofitcrud.bottom_nav

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityBottomNavBinding

class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomAppBar.setNavigationOnClickListener {
            Toast.makeText(this,"Navigation icon clicked",Toast.LENGTH_SHORT).show()
        }

        binding.bottomAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.settingsBottom ->{
                    Toast.makeText(this,"Settings icon clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.appsBottom ->{
                    Toast.makeText(this,"Apps icon clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.add ->{
                    Toast.makeText(this,"Add icon clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.search ->{
                    Toast.makeText(this,"Search icon clicked",Toast.LENGTH_SHORT).show()
                }


            }
            true
        }

    }
}