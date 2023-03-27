package com.example.clearsky.Service

import com.example.clearsky.Class.City
import com.example.clearsky.WeatherForecastResponse
import com.example.clearsky.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("cnt") count: Int = 7,
        @Query("units") units: String = "metric"
    ): WeatherForecastResponse

    @GET("geo/1.0/direct")
    suspend fun getCityCoordinates(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): List<City>
}
