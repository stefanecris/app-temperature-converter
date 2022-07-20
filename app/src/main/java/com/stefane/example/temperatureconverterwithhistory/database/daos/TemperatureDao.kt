package com.stefane.example.temperatureconverterwithhistory.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stefane.example.temperatureconverterwithhistory.database.models.Temperature
import kotlinx.coroutines.flow.Flow

@Dao
interface TemperatureDao {

    @Insert
    fun insert(temperature: Temperature)

    @Query("SELECT * FROM temperature")
    fun getTemperatures(): Flow<List<Temperature>>

}