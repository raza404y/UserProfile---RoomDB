package com.example.form.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database([Form::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class FormDatabase : RoomDatabase() {
    abstract fun getFormDao(): FormDao

    companion object {
        private var INSTANCE: FormDatabase? = null
        fun createData(context: Context): FormDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FormDatabase::class.java,
                    "form_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}