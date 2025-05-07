package org.baghdad.data.mapper

import data.source.remote.weather.model.WeatherResponse
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition

fun WeatherResponse.toDomain(): CurrentWeather {
    return CurrentWeather(
        temperatureInCelsius = main.temp,
        weatherCondition = WeatherCondition.fromDescription(
            weather.firstOrNull()?.description ?: ""
        )
    )
}