package org.baghdad.logic.repository
import org.baghdad.logic.module.entities.CurrentWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): CurrentWeather
}