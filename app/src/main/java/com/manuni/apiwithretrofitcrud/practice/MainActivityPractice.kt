package com.manuni.apiwithretrofitcrud.practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityMainPracticeBinding
import com.manuni.apiwithretrofitcrud.models.UserModel
import com.manuni.apiwithretrofitcrud.networkservices.RetrofitClient
import kotlinx.coroutines.launch

class MainActivityPractice:AppCompatActivity() {
    private lateinit var binding:ActivityMainPracticeBinding

    private val PER_PAGE_DATA:Int = 20
    private var tempPageNumber:Int = 1

    private var users:ArrayList<UserModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()


    }

    private fun listeners(){
        //pagination
        binding.recyclerView.addOnScrollListener(object :OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    recyclerView.layoutManager?.let {
                        val lm = it as LinearLayoutManager
                        val totalItemCount = lm.itemCount
                        if (!binding.swipeRefresh.isRefreshing && totalItemCount==lm.findLastVisibleItemPosition()+1
                            && (totalItemCount % PER_PAGE_DATA)==0){
                            tempPageNumber++
                            getUser(tempPageNumber)

                        }
                    }
                }
            }
        })
    }

    private fun getUser(tempPageNum:Int){
        try {
            lifecycleScope.launch {
                val res = RetrofitClient.retrofit.getUserData(tempPageNumber,PER_PAGE_DATA)

                if (tempPageNum==1){
                    users.clear()
                }

                users.addAll(res)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}