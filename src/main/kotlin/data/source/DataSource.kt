package org.baghdad.data.source

import data.source.weather.model.WeatherResponse

interface DataSource {
    suspend fun fetchCurrentWeather(city: String): WeatherResponse
}