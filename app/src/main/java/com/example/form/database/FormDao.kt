package com.example.form.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(form: Form)

    @Update
    suspend fun update(form: Form)

    @Delete
    suspend fun delete(form: Form)

    @Query("select * from formTable where id = :id")
    fun getById(id:Int):LiveData<Form>


}