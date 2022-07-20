package com.stefane.example.temperatureconverterwithhistory.repository

import androidx.annotation.WorkerThread
import com.stefane.example.temperatureconverterwithhistory.database.daos.TemperatureDao
import com.stefane.example.temperatureconverterwithhistory.database.models.Temperature
import kotlinx.coroutines.flow.Flow

class TemperatureRepository(private val temperatureDao: TemperatureDao) {

    val allTemperatures: Flow<List<Temperature>> = temperatureDao.getTemperatures()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(temperature: Temperature) {
        temperatureDao.insert(temperature)
    }

}