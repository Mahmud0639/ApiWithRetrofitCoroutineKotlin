package com.manuni.apiwithretrofitcrud.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.manuni.apiwithretrofitcrud.MyConstants

@Dao
interface ContactsDao {
    //read
    @Query("SELECT * FROM ${MyConstants.TABLE_NAME}")
    fun getAllContacts():List<ContactsEntity>

    //insert
    @Insert
    fun insertAll(vararg contactsEntity: ContactsEntity)


}


/**
 * first entity then dao then database we need to create
 * entity -> dao(data access object) all the table accessing methods is here like insert,read,update,delete
 * -> database
 */