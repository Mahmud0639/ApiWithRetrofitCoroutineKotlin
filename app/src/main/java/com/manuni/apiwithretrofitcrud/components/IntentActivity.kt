package com.manuni.apiwithretrofitcrud.components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.manuni.apiwithretrofitcrud.databinding.ActivityIntentBinding

class IntentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()

    }

    /**
     * implicit intents
     */
    private fun listeners(){
        binding.dialBtn.setOnClickListener {
            val intent:Intent = Intent(Intent.ACTION_DIAL).apply {
                setData(Uri.parse("tel:01774362950"))
            }
            startActivity(intent)
        }

        binding.locationBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                setData(Uri.parse("geo:1.5133232, 101.3210353"))
            }
            startActivity(intent)
        }

        /**
         * Explicit intents
         */

        binding.nextActivityBtn.setOnClickListener {
            val intent = Intent(this,ServiceActivity::class.java)
            startActivity(intent)
        }
    }
}