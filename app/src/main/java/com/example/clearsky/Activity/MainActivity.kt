package com.example.clearsky.Activity

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clearsky.Adapter.ForecastAdapter
import com.example.clearsky.ForecastItem
import com.example.clearsky.Model.WeatherViewModel
import com.example.clearsky.R
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var textViewCity: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewTemperature: TextView
    private lateinit var btnSearch : Button
    private lateinit var editTextCity : EditText
    private lateinit var recyclerViewForecast : RecyclerView
    private val apiKey = "a2317e7fe800f51f1e2ddebed66a9be8"
    private var lat : Float = 0.0F
    private var lon : Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        textViewCity = findViewById(R.id.textViewCity)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        btnSearch = findViewById(R.id.buttonSearch)
        editTextCity = findViewById(R.id.editTextCitySearch)
        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        btnSearch.setOnClickListener() {
            val location = editTextCity.text.toString()
            if(location.isEmpty()) {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            } else {
                getWeatherForLocation(location)
            }
        }

        // Observe weather data
        viewModel.weather.observe(this, Observer { weather ->
            textViewCity.text = weather.name
            textViewDescription.text = weather.weather[0].description.capitalize(Locale.ROOT)
            textViewTemperature.text = getString(R.string.temperature_format, weather.main.temp)
            lat = weather.coord.lat
            lon = weather.coord.lon

            getForecastForLocation()
        })

        viewModel.forecast.observe(this, Observer { forecast: List<ForecastItem> ->
            displayForecast(forecast)
        })
    }

    private fun getWeatherForLocation(location: String) {
        viewModel.getWeather(location, apiKey)
    }

    private fun getForecastForLocation() {
        viewModel.getForecast(lat, lon, apiKey)
    }

    private fun displayForecast(forecast: List<ForecastItem>) {
        val forecastAdapter = ForecastAdapter(forecast)
        recyclerViewForecast.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
        }
    }
}