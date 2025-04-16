package com.manuni.apiwithretrofitcrud.drawer_layout

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityDrawerBinding

class DrawerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawerBinding
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private val TAG = "DrawerActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = "My Drawer"

        supportActionBar?.setDisplayShowHomeEnabled(true)
        //if we want to set our own icon as ham burger icon
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_add)

        //holding the drawer event when open, close etc
        binding.drawerLayout.addDrawerListener(object : DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                
                Log.d(TAG, "onDrawerSlide: ")
            }

            override fun onDrawerOpened(drawerView: View) {
                Log.d(TAG, "onDrawerOpened: ")
            }

            override fun onDrawerClosed(drawerView: View) {
                Log.d(TAG, "onDrawerClosed: ")
            }

            override fun onDrawerStateChanged(newState: Int) {
                Log.d(TAG, "onDrawerStateChanged: ")
            }
        })

        //toggling, enabled and sync
        mDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,0,0)
        mDrawerToggle.isDrawerIndicatorEnabled = true
        mDrawerToggle.syncState()





    }
}