package com.example.clearsky.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clearsky.data.ForecastItem
import com.example.clearsky.data.RetrofitInstance
import com.example.clearsky.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> = _weather

    fun getWeather(location: String, apiKey: String) {
        viewModelScope.launch(IO) {
            try {
                val response = RetrofitInstance.api.getCurrentWeather(location, apiKey)
                withContext(Main) {
                    _weather.value = response
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather data: ${e.message}")
            }
        }
    }

    fun getWeatherByCoordinates(lon: Float, lat: Float, apiKey: String) {
        viewModelScope.launch(IO) {
            try {
                val response = RetrofitInstance.api.getCurrentWeatherByCoordinates(lat, lon, apiKey)
                withContext(Main) {
                    _weather.value = response
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather data: ${e.message}")
            }
        }
    }

    private val _forecast = MutableLiveData<List<ForecastItem>>()
    val forecast: LiveData<List<ForecastItem>> = _forecast

    fun getForecast(lon: Float, lat: Float, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getForecast(lat, lon, apiKey)
                withContext(Dispatchers.Main) {
                    _forecast.value = response.list
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching forecast data: ${e.message}")
            }
        }
    }

}