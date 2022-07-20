package com.stefane.example.temperatureconverterwithhistory

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.stefane.example.temperatureconverterwithhistory.database.AppDatabase
import com.stefane.example.temperatureconverterwithhistory.database.daos.TemperatureDao
import com.stefane.example.temperatureconverterwithhistory.database.models.Temperature
import com.stefane.example.temperatureconverterwithhistory.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var database : AppDatabase
    private lateinit var temperatureDao : TemperatureDao

    private var degreeCelsius = ""
    private var degreeFahrenheit = ""
    private var degreeKelvin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        database = AppDatabase.getInstance(this)

        temperatureDao = database.temperatureDao()

    }

    override fun onStart() {
        super.onStart()

        addTextChangedListenerInEditCelsius()
        addTextChangedListenerInEditFahrenheit()
        addTextChangedListenerInEditKelvin()

    }

    private fun addTextChangedListenerInEditCelsius() {

        binding.celsiusEdit.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if(binding.celsiusEdit.hasFocus()) {

                    CoroutineScope(Dispatchers.IO).launch {

                        convertsCelsius()

                        withContext(Dispatchers.Main) {

                            binding.apply {
                                fahrenheitEdit.setText(degreeFahrenheit)
                                kelvinEdit.setText(degreeKelvin)
                            }

                        }

                    }

                }

            }

            override fun afterTextChanged(p0: Editable?) { }

        })

    }

    private fun convertsCelsius() {

        degreeCelsius = binding.celsiusEdit.text.toString()

        if(degreeCelsius != ""){
            degreeFahrenheit = "%.3f".format(Temperature.convertsCelsiusToFahrenheit(degreeCelsius.toDouble()))
            degreeKelvin = "%.3f".format(Temperature.convertsCelsiusToKelvin(degreeCelsius.toDouble()))
            saveTemperature(degreeCelsius.toDouble(), degreeFahrenheit.toDouble(), degreeKelvin.toDouble())
        }else{
            degreeFahrenheit = ""
            degreeKelvin = ""
        }

    }

    private fun addTextChangedListenerInEditFahrenheit() {

        binding.fahrenheitEdit.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if(binding.fahrenheitEdit.hasFocus()){

                    CoroutineScope(Dispatchers.IO).launch {

                        convertsFahrenheit()

                        withContext(Dispatchers.Main) {

                            binding.apply {
                                celsiusEdit.setText(degreeCelsius)
                                kelvinEdit.setText(degreeKelvin)
                            }

                        }

                    }

                }


            }

            override fun afterTextChanged(p0: Editable?) { }

        })

    }

    private fun convertsFahrenheit() {

        degreeFahrenheit = binding.fahrenheitEdit.text.toString()

        if(degreeFahrenheit != ""){
            degreeCelsius = "%.3f".format(Temperature.convertsFahrenheitToCelsius(degreeFahrenheit.toDouble()))
            degreeKelvin = "%.3f".format(Temperature.convertsFahrenheitToKelvin(degreeFahrenheit.toDouble()))
            saveTemperature(degreeCelsius.toDouble(), degreeFahrenheit.toDouble(), degreeKelvin.toDouble())

        }else{
            degreeCelsius = ""
            degreeKelvin = ""
        }

    }

    private fun addTextChangedListenerInEditKelvin() {

        binding.kelvinEdit.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if(binding.kelvinEdit.hasFocus()){

                    CoroutineScope(Dispatchers.IO).launch {

                        convertsKelvin()

                        withContext(Dispatchers.Main) {

                            binding.celsiusEdit.setText(degreeCelsius)
                            binding.fahrenheitEdit.setText(degreeFahrenheit)

                        }

                    }

                }

            }

            override fun afterTextChanged(p0: Editable?) { }

        })

    }

    private fun convertsKelvin() {

        degreeKelvin = binding.kelvinEdit.text.toString()

        if(degreeKelvin != ""){
            degreeCelsius = "%.3f".format(Temperature.convertsKelvinToCelsius(degreeKelvin.toDouble()))
            degreeFahrenheit = "%.3f".format(Temperature.convertsKelvinToFahrenheit(degreeKelvin.toDouble()))
            saveTemperature(degreeCelsius.toDouble(), degreeFahrenheit.toDouble(), degreeKelvin.toDouble())
        }else{
            degreeCelsius = ""
            degreeFahrenheit = ""
        }

    }

    private fun saveTemperature(celsius: Double, fahrenheit: Double, kelvin: Double) {
        this.temperatureDao.insert(Temperature(celsius, fahrenheit, kelvin))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_history){
            openHistoryActivity()
            return true
        }
        return false

    }

    private fun openHistoryActivity(){
        startActivity(Intent(this, HistoryActivity::class.java))
    }

}