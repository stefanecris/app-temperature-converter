package com.stefane.example.temperatureconverterwithhistory.ui.viewmodels

import androidx.lifecycle.*
import com.stefane.example.temperatureconverterwithhistory.database.models.Temperature
import com.stefane.example.temperatureconverterwithhistory.repository.TemperatureRepository
import kotlinx.coroutines.launch

class TemperatureViewModel(private val repository: TemperatureRepository) : ViewModel() {

    val allTemperatures: LiveData<List<Temperature>> = repository.allTemperatures.asLiveData()

    fun insert(temperature: Temperature) = viewModelScope.launch {
        repository.insert(temperature)
    }

}

class TemperatureViewModelFactory(private val repository: TemperatureRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TemperatureViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TemperatureViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}