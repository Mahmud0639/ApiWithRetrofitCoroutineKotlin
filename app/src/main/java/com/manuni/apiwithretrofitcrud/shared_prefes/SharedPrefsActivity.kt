package com.manuni.apiwithretrofitcrud.shared_prefes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.manuni.apiwithretrofitcrud.databinding.ActivitySharedPrefsBinding



class SharedPrefsActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySharedPrefsBinding
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPrefsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

       sharedPrefs = EncryptedSharedPreferences.create(
            this,
            "secret_my_shared",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

       // sharedPrefs = getSharedPreferences("my-shared",Context.MODE_PRIVATE) //this is for general shared prefs
        listeners()
    }

    private fun listeners(){
        binding.saveBtn.setOnClickListener {
            val mName = binding.etName.text.toString()
            sharedPrefs.edit().putString("MY_NAME",mName).apply()//apply for asynchronous programming, commit() for synchronous, but recommended is apply()
            //sharedPrefs.edit().putString("MY_ID","033").apply()//apply for asynchronous programming, commit() for synchronous, but recommended is apply()
        }

        binding.readBtn.setOnClickListener {
            val myName = sharedPrefs.getString("MY_NAME","")?:""
            binding.sharedPrefTxt.text = myName
        }

        binding.clearBtn.setOnClickListener {
            //clear specific
           // sharedPrefs.edit().remove("MY_ID").apply()

            binding.etName.text?.clear()
            binding.sharedPrefTxt.text = ""
            sharedPrefs.edit().clear().apply()//clearing all data
        }
    }

}