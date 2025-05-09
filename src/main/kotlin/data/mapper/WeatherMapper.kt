package org.baghdad.data.mapper

import data.source.weather.model.WeatherDto
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition

fun WeatherDto.toDomain(): CurrentWeather {
    return CurrentWeather(
        temperatureInCelsius = main.temp,
        weatherCondition = WeatherCondition.fromDescription(
            weather.firstOrNull()?.description.toString()
        )
    )
}