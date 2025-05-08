package org.baghdad.logic.module.entities

data class CurrentWeather(
    val temperatureInCelsius: Double,
    val weatherCondition: WeatherCondition
)

enum class WeatherCondition {
    Clear,
    Cloudy,
    Overcast,
    Rainy,
    Drizzle,
    Showers,
    Snowy,
    Sleet,
    Windy,
    Stormy,
    Foggy,
    Haze,
    Dust,
    Mist,
    Sunny,
    Unknown;
}