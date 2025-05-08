package org.baghdad.data.repository.weather

import data.source.weather.ApiWeatherDataSource
import org.baghdad.data.mapper.toDomain
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.exceptions.ErrorFetchingWeatherData
import org.baghdad.logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val dataSource: ApiWeatherDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeatherByCityName(city: String): CurrentWeather {
        return try {
            val dto = dataSource.fetchCurrentWeather(city)
            dto.toDomain()
        }
        catch (_: Exception) {
            throw ErrorFetchingWeatherData("Failed to fetch weather data for $city")
        }
    }
}