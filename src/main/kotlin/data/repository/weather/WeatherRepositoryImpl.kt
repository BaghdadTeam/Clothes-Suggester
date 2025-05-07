package org.baghdad.data.repository.weather

import data.source.weather.WeatherDataSource
import org.baghdad.data.mapper.toDomain
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val dataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeatherByCityName(city: String): CurrentWeather {
        val dto = dataSource.fetchCurrentWeather(city)
        return dto.toDomain()
    }
}