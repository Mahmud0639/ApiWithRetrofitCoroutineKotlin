package com.manuni.apiwithretrofitcrud.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manuni.apiwithretrofitcrud.MyConstants

@Entity(tableName = MyConstants.TABLE_NAME)
data class ContactsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String? = "",
    @ColumnInfo(name = "last_name") val lastName: String? = "",
    @ColumnInfo(name = "phone_number") val phoneNumber: String? = ""
)

/**
 * first entity then dao then database we need to create
 * entity -> dao(data access object) all the table accessing methods is here like insert,read,update,delete
 * -> database
 */