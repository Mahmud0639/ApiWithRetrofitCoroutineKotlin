package com.manuni.apiwithretrofitcrud.appbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityAppBarBinding

class AppBarActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAppBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = "App bar and menu"



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings ->{
                Toast.makeText(this,"Settings is clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.about_me ->{
                Toast.makeText(this,"about me is clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.aboutCompany ->{
                Toast.makeText(this,"about company is clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.exit ->{
                Toast.makeText(this,"exit is clicked",Toast.LENGTH_SHORT).show()
                //to exit an activity
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}