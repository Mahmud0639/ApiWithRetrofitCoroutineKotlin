package com.manuni.apiwithretrofitcrud

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.manuni.apiwithretrofitcrud.adapters.UserAdapter
import com.manuni.apiwithretrofitcrud.databinding.ActivityMainBinding
import com.manuni.apiwithretrofitcrud.databinding.AddUserAlertBinding
import com.manuni.apiwithretrofitcrud.models.UserModel
import com.manuni.apiwithretrofitcrud.models.fileupload.FileUpload
import com.manuni.apiwithretrofitcrud.models.locations.LocationModel
import com.manuni.apiwithretrofitcrud.networkservices.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var users: ArrayList<UserModel> = arrayListOf()
    private val userAdapter = UserAdapter(users)

    private val PER_PAGE_DATA = 20
    private var tempPageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()
        listeners()
        //we added all the listeners in one places so that onCreate() would be cleared

        //getUser()
        // saveUserData()


    }

    private fun listeners() {

        binding.photoUploadBtn.setOnClickListener {
            val i = Intent(this,FileUploadActivity::class.java)
            startActivity(i)
        }


        binding.addUserBtn.setOnClickListener {
            addUserAlert(true, null)
        }

        /* In Android, SwipeRefreshLayout.post {} is used when you need to trigger a refresh state programmatically
         and ensure UI updates happen properly.

         Problem Without post {}
         If you try calling swipeRefreshLayout.isRefreshing = true directly inside onCreate()
         or before the UI is fully drawn, it may not work properly because the layout hasn't been measured yet.

         🔹 Solution: Using post {}
         The post {} method schedules the given action (inside {}) to run after the UI has been measured
         and laid out. This ensures that SwipeRefreshLayout is ready to be refreshed.*/

        binding.swipeRefresh.post {
            getUser(tempPageNumber)
        }

        binding.swipeRefresh.setOnRefreshListener {
            tempPageNumber = 1
            getUser(tempPageNumber)
        }

        //pagination started here---
        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    recyclerView.layoutManager?.let {
                        //here it layoutManager
                        val lm = it as LinearLayoutManager
                        val totalItemCount = lm.itemCount

                        if (!binding.swipeRefresh.isRefreshing &&
                            totalItemCount == lm.findLastVisibleItemPosition() + 1 &&
                            /* Why This Condition?
                             This checks if the user has scrolled to the last item in the list.

                             ✅ Example 1 (Scrolling reached the last item)
                             totalItemCount = 20
                             lm.findLastVisibleItemPosition() = 19
                             19 + 1 == 20 ✅ (Load more data)*/

                            (totalItemCount % PER_PAGE_DATA) == 0
                        ) {

                            /*✅ If totalItemCount = 40 and PER_PAGE_DATA = 20:
                            👉 40 % 20 == 0 (We expect more data, so we fetch the next page.)

                            ✅ If totalItemCount = 60 and PER_PAGE_DATA = 20:
                            👉 60 % 20 == 0 (More data might be available.)*/
                            //If true, it means the current data batch is full, and you can load more data.

                            tempPageNumber++
                            getUser(tempPageNumber)
                        }
                    }
                }
            }
        })
    }



    @SuppressLint("SetTextI18n")
    private fun addUserAlert(isSave: Boolean, userModel: UserModel?) {
        tempSelectedLocationIndexes.clear()

        val bindingAlert : AddUserAlertBinding
        val locationsForServer = arrayListOf<Int>()
        val oldLocaIds = arrayListOf<Int>()
        AlertDialog.Builder(this).create().apply {
             bindingAlert = AddUserAlertBinding.inflate(layoutInflater)
            setView(bindingAlert.root)

            if (!isSave) {
                bindingAlert.userTv.text = "Update: ${userModel?.name}"
                bindingAlert.saveBtn.text = "Update"


                bindingAlert.etName.setText(
                    userModel?.name ?: ""
                )//for edittext we need to pass setText(),,,because it required String,,etName.text required Editable that can't be directly converted String to Editable
                //we can like this way if we want to stay on the previous system
                //bindingAlert.etName.text = Editable.Factory.getInstance().newEditable(userModel?.name)
                bindingAlert.etPhone.text = Editable.Factory.getInstance().newEditable(userModel?.phoneNumber ?: "")
                bindingAlert.etAmount.setText(userModel?.amount.toString())
                bindingAlert.etCity.setText(userModel?.address?.city ?: "")
                bindingAlert.etCounty.setText(userModel?.address?.country ?: "")

                var loc = ""
                userModel?.location?.forEachIndexed { index, location ->
                    if (index!=0){
                        loc += ", "
                    }
                    loc += location.locationName?:""
                    location.locationId?.let {
                        oldLocaIds.add(it)
                    }
                }

                bindingAlert.locationsTxt.text = loc

            }

            bindingAlert.cancelBtn.setOnClickListener {
                dismiss()
            }

            bindingAlert.locationBtn.setOnClickListener {

               checkedBox = BooleanArray(locationsList.size)
                locationsList.forEachIndexed { index, locationModel ->
                    locationModel.id?.toInt().let {
                        if (oldLocaIds.contains(it)){
                            checkedBox[index] = true
                        }
                    }
                }
                
                showLocationChooser(locationsList,checkedBox, selected = {s,ids->
                    bindingAlert.locationsTxt.text = s
                    locationsForServer.clear()
                    locationsForServer.addAll(ids)
                }, newLocation = {
                    //we want to refresh our checkbox alert dialog to get new data
                    //so we to click on the addLocationBtn programmatically
                    bindingAlert.locationBtn.performClick()
                }, getDialog = {
                    //here we dismiss the AlertDialog of the save user
                    //bindingAlert.cancelBtn.performClick()

                    //we can also show the location dialog
                    bindingAlert.locationBtn.performClick()

                } )
            }

            bindingAlert.saveBtn.setOnClickListener {
                val name = bindingAlert.etName.text?.trim().toString()
                if (name.isEmpty()) {
                    bindingAlert.etName.error = "Enter name"
                    return@setOnClickListener
                }
                val phone = bindingAlert.etPhone.text?.trim().toString()
                if (phone.isEmpty()) {
                    bindingAlert.etPhone.error = "Enter phone"
                    return@setOnClickListener
                }
                val amount = bindingAlert.etAmount.text?.trim().toString()
                var doubleAmount: Double = 0.0
                if (amount.isNotEmpty()) {
                    doubleAmount = amount.toDouble()

                }
                val city = bindingAlert.etCity.text?.trim().toString()
                if (city.isEmpty()) {
                    bindingAlert.etCity.error = "Enter city"
                    return@setOnClickListener
                }
                val country = bindingAlert.etCounty.text?.trim().toString()
                if (country.isEmpty()) {
                    bindingAlert.etCounty.error = "Enter country"
                    return@setOnClickListener
                }

                saveUserData(isSave, userModel?.userId, name, phone, doubleAmount, city, country,locationsForServer.toTypedArray()) {
                    dismiss()
                }
            }

        }.show()

        bindingAlert.progress.visibility = View.VISIBLE
        getLocations {
            bindingAlert.progress.visibility = View.INVISIBLE
        }
    }
    /* {
         "name": "Touhid Apps 44",
         "phoneNumber": "654321",
         "amount": 5.2,
         "city": "Mymensingh",
         "country": "Bangladesh",
         "locations":[1,2]
     }*/

    //save or update user data in one function
    private fun saveUserData(
        isSave: Boolean,
        uId: Int?,
        name: String,
        phone: String,
        amount: Double,
        city: String,
        country: String,
        mLocations: Array<Int>,
        success: () -> Unit
    ) {
        val body = HashMap<String, Any?>().apply {
            if (!isSave) {
                put("userId", uId)
            }
            put("name", name)
            put("phoneNumber", phone)
            put("amount", amount)
            put("city", city)
            put("country", country)
            put("locations", mLocations)

        }
        //as the saveUser() is a suspend function so we need to put them in a suspend scope here
        /* lifecycleScope.launch {
             try {
                 val res = RetrofitClient.retrofit.saveUser(body)
                 println("onResponse: "+res.result)
                 success()
             }catch (e:Exception){
                 e.printStackTrace()
                 println("API Error: ${e.message}")
             }

         }*/
        lifecycleScope.launch {
            try {
                val res = if (isSave) {
                    RetrofitClient.retrofit.saveUser(body)
                } else {
                    RetrofitClient.retrofit.updateUser(body)
                }

                if (res.isSuccessful) {
                    println("onResponse: " + res.body()?.result)
                    success()

                    //now we want to reload the page because new data has come
                    tempPageNumber = 1
                    getUser(tempPageNumber)

                } else {
                    println("API Error: ${res.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("API Exception: ${e.message}")
            }
        }


    }

    /*    private fun deleteUser(userModel: UserModel?){
            AlertDialog.Builder(this).apply {
                setTitle("Delete user: ${userModel?.name} ?")
                setMessage("Do You Want to Delete?")
                setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                    override fun onClick(v: View?) {
                        performDelete(userModel)
                    }
                })
                setNegativeButton("No",object : DialogInterface.OnClickListener{
                    override fun onClick(v: View?) {

                    }
                })
            }.show()
        }*/

    private fun deleteUser(userModel: UserModel?) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete user: ${userModel?.name} ?")
            setMessage("Do You Want to Delete?")
            setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    performDelete(userModel)
                }
            })
            setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss() // Dismiss the dialog when "No" is clicked
                }
            })
        }.show()
    }

    private val locationsList:ArrayList<LocationModel> = arrayListOf()
    private var checkedBox:BooleanArray = BooleanArray(0)
    //we need the same number of check box of locations same number
    private fun getLocations(success: (List<LocationModel>) -> Unit) {
        lifecycleScope.launch {
            val res = RetrofitClient.retrofit.getLocations()

            locationsList.clear()
            locationsList.addAll(res)
            checkedBox = BooleanArray(locationsList.size)

            success(res)
        }



    }

    private var tempSelectedLocationIndexes = arrayListOf<Int>()
    private var checkedIndex = BooleanArray(0)

    private fun showLocationChooser(mLocations:List<LocationModel>,checkedItems:BooleanArray,selected:(String,ArrayList<Int>)->Unit,newLocation:()->Unit,getDialog:()->Unit){
        AlertDialog.Builder(this).apply {
            setTitle("Location you visited")
            setCancelable(false)


            val mData = arrayListOf<String>()
            mLocations.forEach {
                mData.add(it.locationName ?:"")
            }

            //converting arrayList to array
            val arrayDataFromArrayList = arrayOf(*mData.toTypedArray())
            checkedIndex = checkedItems.clone()//clone is used to put in another address location of memory to prevent update the reference data

            if (tempSelectedLocationIndexes.isNotEmpty()){
                locationsList.forEachIndexed { index, locationModel ->
                    if (tempSelectedLocationIndexes.contains(index)){
                        checkedIndex[index] = true
                    }else{
                        checkedIndex[index] = false
                    }
                }
            }

            setMultiChoiceItems(arrayDataFromArrayList,checkedIndex,){ dialog,mIndex,isChecked->

            }

            setPositiveButton("Ok"){p0,p1->
                val mCheckedItems = (p0 as AlertDialog).listView.checkedItemPositions
                tempSelectedLocationIndexes.clear()
                mCheckedItems.forEach { key, value ->
                    if (value){
                        tempSelectedLocationIndexes.add(key)
                    }
                }

                var s = ""
                var ids = ArrayList<Int>()

                locationsList.forEachIndexed { index, locationModel ->
                    if (tempSelectedLocationIndexes.contains(index)){
                        if (s.isNotEmpty()){
                            s += ", "
                        }
                        s += locationModel.locationName?:""
                        locationModel.id?.toInt()?.let {
                            ids.add(it)
                        }
                    }
                }

                selected(s,ids)

            }

            setNegativeButton("Cancel"){ p0,p1->

            }

            setNeutralButton("Add Location"){ p0,p1 ->

                getDialog()

                alertNewLocation {
                    newLocation()

                }
            }
        }.show()
    }

    private fun alertNewLocation(success: () -> Unit){
        AlertDialog.Builder(this).apply {
            setTitle("Enter location name")
            val et = EditText(this@MainActivity)
            et.hint = "Location name"
            setView(et)

            setPositiveButton("Save",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    saveLocation(et.text.toString()){
                        getLocations {
                            success()
                        }

                    }
                }
            })

            setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })


        }.show()
    }






private fun saveLocation(location:String,success: () -> Unit){
    lifecycleScope.launch {
        try {
            val body = HashMap<String,Any?>().apply {
                put("locationName",location)
            }
            val res = RetrofitClient.retrofit.saveLocation(body)
            println("SaveLocationResponse: ${res.result}")
            Toast.makeText(this@MainActivity,"${res.result}",Toast.LENGTH_LONG).show()
            success()
        }catch (e:Exception){
            e.printStackTrace()
            println("ErrorSaveLocation: ${e.message}")
        }

    }
}

    private fun performDelete(userModel: UserModel?) {
        try {
            lifecycleScope.launch {
                val body = HashMap<String, Any?>().apply {
                    put("userId", userModel?.userId)
                }
                val res = RetrofitClient.retrofit.deleteUser(body)
                Toast.makeText(this@MainActivity, "${res.body()?.result}", Toast.LENGTH_LONG).show()

                tempPageNumber = 1
                getUser(tempPageNumber)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            println("performDelete: ${e.message}")
        }
    }

    private fun initUI() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = userAdapter
        userAdapter.setItemClick {
            val i = Intent(this, DetailsUserActivity::class.java)
            i.putExtra("USER_DATA", it)//it means UserModel
            startActivity(i)
        }

        userAdapter.setEditClick {//"it" returning userModel object
            addUserAlert(false, it)
        }

        userAdapter.setDeleteClick {
            deleteUser(it)
        }

    }

    private fun getUser(temPageNumber: Int) {
        /*lifecycleScope.launch {} runs in the main thread by default
        lifecycleScope.launch { ... } runs in a background thread
        by default only if the function inside it is marked as suspend. */

        binding.swipeRefresh.isRefreshing = true

        lifecycleScope.launch {
            try {
                val res = RetrofitClient.retrofit.getUserData(tempPageNumber, PER_PAGE_DATA)
                //println("onResponse: "+res)
                //if this api is calling multiple time then the duplicate data will show

                binding.swipeRefresh.isRefreshing = false

                if (temPageNumber == 1) {
                    users.clear()//because if page 2,3,4,... then not clear only when it is 1
                    //getUser(1)
                }

                users.addAll(res)
                userAdapter.notifyDataSetChanged()

            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: $e")
            }
        }
    }


}