package org.baghdad.data.source

import data.source.weather.model.WeatherDto

interface  WeatherDataSource {
    suspend fun fetchCurrentWeather(city: String): WeatherDto
}
