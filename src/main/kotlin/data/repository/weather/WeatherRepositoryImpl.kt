package org.baghdad.data.repository.weather

import data.source.remote.weather.WeatherRemoteSource
import org.baghdad.data.mapper.toDomain
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val remoteSource: WeatherRemoteSource
) : WeatherRepository {

    override suspend fun getCurrentWeatherByCityName(city: String): CurrentWeather {
        val dto = remoteSource.fetchCurrentWeather(city)
        return dto.toDomain()
    }
}