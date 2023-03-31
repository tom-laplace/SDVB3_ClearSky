package com.example.clearsky.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clearsky.R
import com.example.clearsky.adapter.ForecastAdapter
import com.example.clearsky.data.ForecastItem
import com.example.clearsky.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var loadingLayout: LinearLayout
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


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        checkLocationPermission()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textViewCity = findViewById(R.id.textViewCity)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        btnSearch = findViewById(R.id.buttonSearch)
        editTextCity = findViewById(R.id.editTextCitySearch)
        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)
        loadingLayout = findViewById(R.id.layout_loading)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        btnSearch.setOnClickListener() {
            val location = editTextCity.text.toString()
            if(location.isEmpty()) {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            } else {
                getWeatherForLocation(location)
            }
            hideKeyboard(it)
        }

        viewModel.weather.observe(this, Observer { weather ->
            textViewCity.text = weather.name
            textViewDescription.text = weather.weather[0].description.replaceFirstChar { it.uppercase() }
            textViewTemperature.text = getString(R.string.temperature_format, weather.main.temp)
            lat = weather.coord.lat
            lon = weather.coord.lon

            getForecastForLocation()
        })

        viewModel.forecast.observe(this, Observer { forecast: List<ForecastItem> ->
            hideLoading()
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

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getLastKnownLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLastKnownLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.longitude.toFloat()
                val longitude = location.latitude.toFloat()
                getWeatherForLocationByCoordinates(latitude, longitude)
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeatherForLocationByCoordinates(latitude: Float, longitude: Float) {
        viewModel.getWeatherByCoordinates(latitude, longitude, apiKey)
        getForecastForLocation()
    }

    private fun showLoading() {
        loadingLayout.visibility = View.VISIBLE
        recyclerViewForecast.visibility = View.GONE
    }

    private fun hideLoading() {
        loadingLayout.visibility = View.GONE
        recyclerViewForecast.visibility = View.VISIBLE
    }


}