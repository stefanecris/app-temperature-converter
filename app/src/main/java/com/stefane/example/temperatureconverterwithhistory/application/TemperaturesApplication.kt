package com.stefane.example.temperatureconverterwithhistory.application

import android.app.Application
import com.stefane.example.temperatureconverterwithhistory.database.AppDatabase
import com.stefane.example.temperatureconverterwithhistory.repository.TemperatureRepository

class TemperaturesApplication : Application() {

    val database by lazy { AppDatabase.getInstance(this) }

    val repository by lazy { TemperatureRepository(database.temperatureDao()) }

}