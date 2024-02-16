package com.panku.quoteapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "contact")
data class ContactData(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val name: String,
    val phone: String,
    val createdDate : Date,
    val isActive: Int
)
