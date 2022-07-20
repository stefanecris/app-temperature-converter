package com.stefane.example.temperatureconverterwithhistory.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stefane.example.temperatureconverterwithhistory.R
import com.stefane.example.temperatureconverterwithhistory.database.models.Temperature

class TemperatureListAdapter : ListAdapter<Temperature, TemperatureListAdapter.TemperatureViewHolder>(TemperaturesComparators()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemperatureViewHolder {
        return TemperatureViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TemperatureViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.celsius.toString() + " °C | " + current.fahrenheit.toString() + " °F | " + current.kelvin.toString() + " K")
    }

    class TemperatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val temperatureItemView: TextView = itemView.findViewById(R.id.text_item)

        fun bind(text: String?) {
            temperatureItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): TemperatureViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
                return TemperatureViewHolder(view)
            }
        }

    }

    class TemperaturesComparators : DiffUtil.ItemCallback<Temperature>() {

        override fun areContentsTheSame(oldItem: Temperature, newItem: Temperature): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Temperature, newItem: Temperature): Boolean {
            return oldItem.celsius == newItem.celsius
        }

    }

}