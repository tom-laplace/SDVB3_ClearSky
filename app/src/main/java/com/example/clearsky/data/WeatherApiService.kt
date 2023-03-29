package com.example.clearsky.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid") apiKey: String,
        @Query("cnt") count: Int = 7,
        @Query("units") units: String = "metric"
    ): WeatherForecastResponse
}
