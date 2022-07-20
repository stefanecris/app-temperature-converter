package com.stefane.example.temperatureconverterwithhistory.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stefane.example.temperatureconverterwithhistory.database.daos.TemperatureDao
import com.stefane.example.temperatureconverterwithhistory.database.models.Temperature

@Database(entities = [Temperature::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun temperatureDao(): TemperatureDao

    companion object {

        private const val DATABASE_NAME: String = "temperatures_bd"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            ).build()

    }

}