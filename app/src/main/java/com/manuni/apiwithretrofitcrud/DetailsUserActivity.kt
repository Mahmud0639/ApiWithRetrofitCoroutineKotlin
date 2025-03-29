package com.manuni.apiwithretrofitcrud

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.databinding.ActivityDetailsUserBinding
import com.manuni.apiwithretrofitcrud.models.UserModel

class DetailsUserActivity : AppCompatActivity() {

   private lateinit var binding: ActivityDetailsUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mUser = intent.getParcelableExtra("USER_DATA") as UserModel?
        mUser?.let {//?. safe call operator checks NOT NULL
            binding.userName.text = "Name: ${it.name}"
            binding.userPhone.text = "Phone: ${it.phoneNumber}"
            binding.userAmount.text = "Amount: ${it.amount}"
            binding.userAddress.text = "Address: ${it.address?.city?:""},${it.address?.country?:""}"

            //since our location is an array we need a foreach loop for retrieving data
            var userLocation = ""
            it.location.forEachIndexed { index, location ->
                if (index!=0){
                    userLocation += ", "
                }
                userLocation += location.locationName?:""
            }
            binding.userLocation.text = "Visited: "+userLocation
        }
    }
}