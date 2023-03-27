package com.example.clearsky.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clearsky.DailyWeather
import com.example.clearsky.Instance.RetrofitInstance
import com.example.clearsky.WeatherResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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

    private val _forecast = MutableLiveData<List<DailyWeather>>()
    val forecast: LiveData<List<DailyWeather>> = _forecast

    fun getForecast(city: String, apiKey: String) {
        viewModelScope.launch(IO) {
            try {
                val response = RetrofitInstance.api.getForecast(city, apiKey)
                withContext(Main) {
                    _forecast.value = response.daily
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching forecast data: ${e.message}")
            }
        }
    }

}