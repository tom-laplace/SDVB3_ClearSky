package com.example.clearsky.data

data class WeatherForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt: Long,
    val main: ForecastMain,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Float,
    val sys: ForecastSys,
    val dt_txt: String
) {
    val weatherIconUrl: String
        get() = "http://openweathermap.org/img/w/${weather[0].icon}.png"
}

data class ForecastMain(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Float
)

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Float,
    val deg: Int,
    val gust: Float
)

data class ForecastSys(
    val pod: String
)