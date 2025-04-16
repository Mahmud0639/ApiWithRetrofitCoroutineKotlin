package com.manuni.apiwithretrofitcrud

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.manuni.apiwithretrofitcrud.databinding.ActivityFragmentBinding

class SnackBarActivity:AppCompatActivity() {
    private lateinit var binding: ActivityFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * String is a specific class that holds text.
         *
         * CharSequence is an interface that String, StringBuilder, Spannable, etc. all implement.
         *
         * So, every String is a CharSequence, but not every CharSequence is a String.
         */

        binding.addFragmentBtn.setOnClickListener {
            Snackbar.make(/*it*we can use it here*/binding.root,"This is a snackbar",Snackbar.LENGTH_INDEFINITE).apply {
                setAction("Dismiss",object: View.OnClickListener{
                    override fun onClick(v: View?) {
                        dismiss()
                    }
                })
            }.show()
        }
    }
}