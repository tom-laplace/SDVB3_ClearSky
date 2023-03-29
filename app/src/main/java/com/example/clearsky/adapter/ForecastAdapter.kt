package com.example.clearsky.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.clearsky.data.ForecastItem
import com.example.clearsky.R
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(private val forecast: List<ForecastItem>): RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val textViewTemperature: TextView = view.findViewById(R.id.textViewTemperature)
        val textViewMinTemperature: TextView = view.findViewById(R.id.textViewMinTemperature)
        val textViewMaxTemperature: TextView = view.findViewById(R.id.textViewMaxTemperature)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = forecast[position]

        holder.textViewDate.text = formatDate(currentItem.dt_txt)
        holder.textViewTemperature.text = "${currentItem.main.temp}°C"
        holder.textViewMinTemperature.text = "Min temp: ${currentItem.main.temp_min}°C"
        holder.textViewMaxTemperature.text = "Max temp: ${currentItem.main.temp_max}°C"

       holder.itemView.findViewById<ImageView>(R.id.imageViewWeatherIcon).load(currentItem.weatherIconUrl)
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return if (date != null) {
            outputFormat.format(date)
        } else {
            "Invalid Date"
        }
    }

    override fun getItemCount(): Int = forecast.size
}