package com.manuni.apiwithretrofitcrud.webview

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manuni.apiwithretrofitcrud.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //loadUrl
        binding.webView.webViewClient = WebViewClient()//it will load url in the app not another app
        binding.webView.loadUrl("https://google.com")
        binding.webView.settings.javaScriptEnabled = true//for showing any type of menu in the website
        //zoom
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = true//it will show the zoom in zoom out plus minus buttons

        binding.backBtn.setOnClickListener {
            if (binding.webView.canGoBack()){
                binding.webView.goBack()
            }

        }

        binding.forwardBtn.setOnClickListener {
            if (!isOnline(this)){
                Toast.makeText(this,"No internet available",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.webView.canGoForward()){
                binding.webView.goForward()
            }
        }


    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        }else{
            super.onBackPressedDispatcher.onBackPressed()
        }

    }

    //checking internet is available or not,,,returning data as true or false
    private fun isOnline(context:Context):Boolean{
        val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

        /**
         * we can also use broadcast receiver to check internet connection
         * it required while a user is already opened an app but internet is not available
         * somehow like wifi is disconnected for the load shedding or any other reason
         * we can give a message to the user,,No internet as snack bar
         */

    }
}