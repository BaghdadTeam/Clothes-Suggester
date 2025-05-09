package data.datasource

import data.dto.WeatherDto

interface  WeatherDataSource {
    suspend fun fetchCurrentWeather(city: String): WeatherDto
}