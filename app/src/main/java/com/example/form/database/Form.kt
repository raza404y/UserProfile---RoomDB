package com.example.form.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.form.Constants

@Entity(tableName = "formTable")
data class Form(

    @PrimaryKey(autoGenerate = false)
    val id: Int? = Constants.ID,

    var name: String?,
    var phone: String?,
    var email: String?,
    var gender: String?,
    var interests: List<String>,
    var emailNotification:Boolean = false,
    var smsNotification: Boolean = false


)
