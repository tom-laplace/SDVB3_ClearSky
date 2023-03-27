package com.example.clearsky.Activity

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        textViewCity = findViewById(R.id.textViewCity)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        btnSearch = findViewById(R.id.buttonSearch)
        editTextCity = findViewById(R.id.editTextCitySearch)

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
        })
    }

    // Function to get weather data for location entered by user
    private fun getWeatherForLocation(location: String) {
        val apiKey = "a2317e7fe800f51f1e2ddebed66a9be8"
        viewModel.getWeather(location, apiKey)
    }
}