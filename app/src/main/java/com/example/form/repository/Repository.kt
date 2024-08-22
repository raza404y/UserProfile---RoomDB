package com.example.form.repository

import androidx.lifecycle.LiveData
import com.example.form.database.Form
import com.example.form.database.FormDao

class Repository(private val dao: FormDao) {

    suspend fun insert(form: Form){
        dao.insert(form)
    }

    suspend fun update(form: Form){
        dao.update(form)
    }

    suspend fun delete(form: Form){
        dao.delete(form)
    }

    fun getById(id: Int):LiveData<Form>{
       return dao.getById(id)
    }

}