package org.baghdad.data.repository.weather

import data.source.weather.WeatherDataSource
import org.baghdad.data.mapper.toDomain
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val dataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeatherByCityName(city: String): CurrentWeather {
        return try {
            val dto = dataSource.fetchCurrentWeather(city)
            dto.toDomain()
        } catch (e: Exception) {
            CurrentWeather(0.0, WeatherCondition.Unknown)
        }
    }
}