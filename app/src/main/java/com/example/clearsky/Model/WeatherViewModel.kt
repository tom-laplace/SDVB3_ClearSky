package com.example.clearsky.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clearsky.Class.City
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
    fun getWeatherForecast(latitude: Double, longitude: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                val forecastResponse = RetrofitInstance.api.getWeatherForecast(latitude, longitude, apiKey = apiKey)
                _forecast.value = forecastResponse.daily
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather forecast data: ${e.message}")
            }
        }
    }


    private val _cityCoordinates = MutableLiveData<City>()
    val cityCoordinates: LiveData<City> = _cityCoordinates

    fun getCityCoordinates(cityName: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val cityList = RetrofitInstance.api.getCityCoordinates(cityName, apiKey = apiKey)
                if (cityList.isNotEmpty()) {
                    _cityCoordinates.value = cityList[0]
                } else {
                    Log.e("WeatherViewModel", "Error fetching city coordinates: No city found")
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching city coordinates: ${e.message}")
            }
        }
    }

}