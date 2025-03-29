package com.manuni.apiwithretrofitcrud.practice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityPracticeBinding
import com.manuni.apiwithretrofitcrud.models.locations.LocationModel

class PracticeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listeners()
        val locations = arrayListOf<LocationModel>()
        val checkedItem = BooleanArray(5)
        showLocationChooser(locations,checkedItem, selected = { data1,data2->

        })
    }

    private fun listeners(){

    }

    var tempSelectedLocationIndex = arrayListOf<Int>()
        var checkedIndex = BooleanArray(0)
    private fun showLocationChooser(locationList:List<LocationModel>,checkedItems:BooleanArray,selected:(String,ArrayList<Int>)->Unit){
        AlertDialog.Builder(this).apply {
            setTitle("Locations You Visited")
            setCancelable(false)

            //at first we need to extract all the location name from the list then convert them to array
            val mData = arrayListOf<String>()
            locationList.forEach {
                mData.add(it.locationName?:"")
            }

            val arrayData = arrayOf(*mData.toTypedArray())
            checkedIndex = checkedItems.clone()

           tempSelectedLocationIndex = arrayListOf<Int>()
            if (tempSelectedLocationIndex.isNotEmpty()){
                locationList.forEachIndexed { index, locationModel ->
                    if (tempSelectedLocationIndex.contains(index)){
                        checkedIndex[index] = true
                    }else{
                        checkedIndex[index] = false
                    }
                }
            }

            setMultiChoiceItems(arrayData,checkedIndex) { dialog,mIndex,isChecked->

            }

            setPositiveButton("Ok"){ p0,p1->
                val listCheckedPos = (p0 as AlertDialog).listView.checkedItemPositions
                tempSelectedLocationIndex.clear()
                listCheckedPos.forEach { key, value ->
                    if (value){
                        tempSelectedLocationIndex.add(key)
                    }

                }
                var s = ""
                var ids = ArrayList<Int>()

                locationList.forEachIndexed { index, locationModel ->
                    if (tempSelectedLocationIndex.contains(index)){
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

            setNegativeButton("Cancel"){p0,p1->

            }

            setNegativeButton("Add Location"){p0,p1->

            }

        }
    }

}