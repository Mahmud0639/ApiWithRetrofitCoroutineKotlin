package com.manuni.apiwithretrofitcrud.networkservices

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val TIME_OUT: Long = 120
    //private val gson = GsonBuilder().setLenient().create()
    private val gson = GsonBuilder().create()
    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

    //here retrofit is an instance of RetrofitInterface
    //this is the syntax of creating an instance of an interface
    //lazy is used for create instance only when the interface is needed
    val retrofit: RetrofitInterface by lazy {
        Retrofit.Builder()
        /*In Retrofit, API responses are usually in JSON format.
        However, Retrofit cannot directly understand JSON.
        It needs a converter to convert JSON into Kotlin/Java objects.*/
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(AllApi.BASEURL)
            .client(okHttpClient)
            .build().create(RetrofitInterface::class.java)
    }
}