package com.example.clearsky

data class WeatherForecastResponse(
    val daily: List<DailyWeather>
)

data class DailyWeather(
    val dt: Long,
    val temp: Temperature,
    val weather: List<Weather>
)

data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double
)