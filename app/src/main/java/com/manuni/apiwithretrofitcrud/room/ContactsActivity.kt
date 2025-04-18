package com.manuni.apiwithretrofitcrud.room

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.App
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityContactsBinding

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //sample data
        val data = arrayListOf(ContactsEntity(firstName = "Mahmud Islam", lastName = "Bappi", phoneNumber = "01774"),
            ContactsEntity(firstName = "Nasima", lastName = "Begum", phoneNumber = "01639"))
        //we can pass as array
        //arrayList to vararg type so we need * and toTypedArray()
        App.db.contactsDao().insertAll(*data.toTypedArray())


        /*
        //we can give like this way as it expected ContactsEntity type data
            #as we give the vararg so we can pass multiple at a time
        App.db.contactsDao().insertAll(ContactsEntity(firstName = "Mahmud Islam", lastName = "Bappi", phoneNumber = "01774"),
            ContactsEntity(firstName = "Nasima", lastName = "Begum", phoneNumber = "01639"))
            */




    }
}