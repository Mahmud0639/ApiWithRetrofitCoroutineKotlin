package com.manuni.apiwithretrofitcrud

import android.app.Application
import com.manuni.apiwithretrofitcrud.room.AppDatabase

class App:Application() {
    companion object{
        lateinit var db: AppDatabase

    }

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDb(applicationContext)
    }

}