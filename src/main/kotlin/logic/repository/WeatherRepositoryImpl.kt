package org.baghdad.logic.repository

import data.source.remote.wheather.WeatherRemoteSource
import org.baghdad.data.mapper.toDomain
import org.baghdad.logic.module.entities.CurrentWeather

class WeatherRepositoryImpl(
    private val remoteSource: WeatherRemoteSource
) : WeatherRepository {

    override suspend fun getCurrentWeatherByCityName(city: String): CurrentWeather {
        val dto = remoteSource.fetchCurrentWeather(city)
        return dto.toDomain()
    }
}