package com.example.clearsky.Instance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clearsky.DailyWeather
import com.example.clearsky.R
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(var forecastList: List<DailyWeather>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewForecastDescription: TextView = itemView.findViewById(R.id.textViewForecastDescription)
        val textViewForecastTemperature: TextView = itemView.findViewById(R.id.textViewForecastTemperature)
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp * 1000L))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = forecastList[position]
        holder.textViewDate.text = convertTimestampToDate(currentItem.dt)
        holder.textViewForecastDescription.text = currentItem.weather[0].description.capitalize(
            Locale.getDefault())
        holder.textViewForecastTemperature.text = holder.itemView.context.getString(
            R.string.temperature_format,
            currentItem.temp.day
        )
    }

    override fun getItemCount() = forecastList.size

    fun updateForecast(forecastResponse: List<DailyWeather>?) {
        if (forecastResponse != null) {
            forecastList = forecastResponse
            notifyDataSetChanged()
        }
    }
}
