package com.example.clearsky.data

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String,
    val coord: Coord
)

data class Weather(
    val description: String,
    val icon: String
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

data class Coord(
    val lon: Float,
    val lat: Float
)