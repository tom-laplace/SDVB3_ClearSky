package com.example.clearsky

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var textViewCity: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewTemperature: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCity = findViewById(R.id.textViewCity)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewTemperature = findViewById(R.id.textViewTemperature)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        val apiKey = "a2317e7fe800f51f1e2ddebed66a9be8"
        viewModel.getWeather("Bordeaux", apiKey)

        viewModel.weather.observe(this, Observer { weatherResponse ->
            textViewCity.text = weatherResponse.name
            textViewDescription.text = weatherResponse.weather[0].description.capitalize(Locale.getDefault())
            textViewTemperature.text = getString(R.string.temperature_format, weatherResponse.main.temp)
        })
    }
}