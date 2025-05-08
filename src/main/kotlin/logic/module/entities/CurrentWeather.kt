package org.baghdad.logic.module.entities

data class CurrentWeather(
    val temperatureInCelsius: Double,
    val weatherCondition: WeatherCondition
)