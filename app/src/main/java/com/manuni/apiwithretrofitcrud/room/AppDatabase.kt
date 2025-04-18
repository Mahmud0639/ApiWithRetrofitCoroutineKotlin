package com.manuni.apiwithretrofitcrud.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manuni.apiwithretrofitcrud.MyConstants

@Database(entities = [ContactsEntity::class], version = MyConstants.LATEST_VERSION, exportSchema = true)
abstract class AppDatabase:RoomDatabase() {
    //here ContactDao is an interface, we are returning an interface while calling the contactsDao function, so that
    //we can access all the methods that are already have in the interface
    abstract fun contactsDao(): ContactsDao

    companion object{
        lateinit var mDb: AppDatabase

        //where should we call the getDb fun?
        //it will call immediately when the app is active so we need to put it under the application class

        fun getDb(context: Context):AppDatabase{
            mDb = Room.databaseBuilder(
                context,AppDatabase::class.java,MyConstants.DATABASE_NAME
            ).addCallback(roomCallBack)
                .allowMainThreadQueries()
                .build()
                .also { 
                    //if we want to see where the database is created we can here
                    Log.d("<ROOM_TAG>", it.openHelper.writableDatabase.path?:"")
                }
            return mDb
        }

        private val roomCallBack = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //if we need to pass any default value we can do here after creating the database
            }
        }
    }
}

/**
 * exportSchema = true is required because we want to get a json file of the table data so that we can read properly
 * what about the table and also we need to tell the compiler where should it put the json file
 * so in the build.gradle kts file under defaultConfig we need to write ksp and see code---
 *     ------------------------------------------------
 *       ksp{
 *             arg("room.schemaLocation","$projectDir/schemas")
 *         }
 *     ----------------------------------------------------
 *       ##  schemas or any name we want we give here no problem
 */