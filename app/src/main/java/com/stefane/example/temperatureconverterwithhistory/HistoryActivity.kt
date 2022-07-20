package com.stefane.example.temperatureconverterwithhistory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stefane.example.temperatureconverterwithhistory.application.TemperaturesApplication
import com.stefane.example.temperatureconverterwithhistory.database.models.Temperature
import com.stefane.example.temperatureconverterwithhistory.databinding.ActivityHistoryBinding
import com.stefane.example.temperatureconverterwithhistory.ui.adapters.TemperatureListAdapter
import com.stefane.example.temperatureconverterwithhistory.ui.viewmodels.TemperatureViewModel
import com.stefane.example.temperatureconverterwithhistory.ui.viewmodels.TemperatureViewModelFactory

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TemperatureListAdapter
    private lateinit var binding: ActivityHistoryBinding

    private val temperatureViewModel : TemperatureViewModel by viewModels {
        TemperatureViewModelFactory((application as TemperaturesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.recyclerView = this.binding.recyclerListTemperatures
        this.adapter = TemperatureListAdapter()
        this.recyclerView.adapter = this.adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()

        temperatureViewModel.allTemperatures.observe(this, Observer { temperatures ->
            temperatures?.let {
                adapter.submitList(it)
            }
        })

    }

}