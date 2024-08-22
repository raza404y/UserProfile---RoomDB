package com.example.form.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.form.database.Form
import com.example.form.database.FormDatabase
import com.example.form.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormViewModel(application: Application):AndroidViewModel(application) {

    lateinit var repository: Repository
    init {
        val dao = FormDatabase.createData(application).getFormDao()
        repository = Repository(dao)
    }

    fun insert(form: Form,inserted:()->Unit) = viewModelScope.launch (Dispatchers.IO){
        repository.insert(form)
        withContext(Dispatchers.Main){
            inserted()
        }
    }

    fun update(form: Form,updated:()->Unit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(form)
        withContext(Dispatchers.Main){
            updated()
        }
    }

    fun delete(form: Form,deleted:()->Unit) = viewModelScope.launch (Dispatchers.IO){
        repository.delete(form)
        withContext(Dispatchers.Main){
            deleted()
        }
    }

    fun getById(id: Int):LiveData<Form>{
       return repository.getById(id)
    }

}