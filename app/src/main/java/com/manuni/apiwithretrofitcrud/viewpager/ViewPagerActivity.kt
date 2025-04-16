package com.manuni.apiwithretrofitcrud.viewpager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityViewPagerBinding

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewpager
        val fragments:List<Fragment> = listOf(FirstFragment(),SecondFragment())

        val adapter = ViewPagerAdapter(fragments,supportFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter

        //tab layout

        //we can customize tabLayout programmatically
        /*binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.your_tab_background_color))
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this,R.color.your_unselected_text_color),ContextCompat.getColor(this,R.color.your_selected_text_color))
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.your_indicator_color))*/

        val fragmentsArray = arrayOf("First","Second")
        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position ->
            tab.setText(fragmentsArray[position])
        }.attach()
    }
}