package com.stefane.example.temperatureconverterwithhistory.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Temperature(
    @ColumnInfo val celsius : Double,
    @ColumnInfo val fahrenheit : Double,
    @ColumnInfo val kelvin : Double
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0

    companion object {

        fun convertsCelsiusToFahrenheit(degreeCelsius : Double) : Double {
            return degreeCelsius * (9.0 / 5.0) + 32
        }

        fun convertsCelsiusToKelvin(degreeCelsius : Double) : Double {
            return degreeCelsius + 273.15
        }

        fun convertsFahrenheitToCelsius(degreeFahrenheit : Double) : Double {
            return (degreeFahrenheit - 32) * (5.0 / 9.0)
        }

        fun convertsFahrenheitToKelvin(degreeFahrenheit : Double) : Double {
            return (degreeFahrenheit - 32.0) * (5.0 / 9.0) + 273.15
        }

        fun convertsKelvinToCelsius(degreeKelvin : Double) : Double{
            return degreeKelvin - 273.15
        }

        fun convertsKelvinToFahrenheit(degreeKelvin : Double) : Double{
            return (degreeKelvin - 273.15) * (9.0 / 5.0) + 32
        }

    }

}
