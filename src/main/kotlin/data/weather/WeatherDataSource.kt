package org.baghdad.data.weather

import data.dto.WeatherDto

interface  WeatherDataSource {
    suspend fun fetchCurrentWeather(city: String): WeatherDto
}