package com.panku.quoteapplication.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.panku.quoteapplication.data.ContactData

@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact : ContactData)

    @Update
    suspend fun updateContact(contact: ContactData)

    @Delete
    suspend fun deleteContact(contact: ContactData)

    @Query("SELECT * FROM contact")
    fun getContact() : LiveData<List<ContactData>>

}