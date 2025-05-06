package org.baghdad.logic.module.entities

data class CurrentWeather(
    val temperatureInCelsius: Float,
    val weatherCondition: WeatherCondition
)