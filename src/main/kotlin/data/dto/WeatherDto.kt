package data.source.weather.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

@Serializable
data class Main(val temp: Double, val humidity: Int)

@Serializable
data class Weather(val main: String, val description: String?)

@Serializable
data class Wind(val speed: Double)