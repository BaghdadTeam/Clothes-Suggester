package org.baghdad.data.mapper

import data.source.remote.wheather.model.OpenWeatherResponse
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition

fun OpenWeatherResponse.toDomain(): CurrentWeather {
    return CurrentWeather(
        temperatureInCelsius = main.temp,
        weatherCondition = WeatherCondition.fromDescription(
            weather.firstOrNull()?.description ?: ""
        )
    )
}