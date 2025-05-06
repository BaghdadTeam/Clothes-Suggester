package org.baghdad.logic.repository

import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather


interface ClothesRepository {
    suspend fun getClothesByWeatherStatus(currentWeather: CurrentWeather): ClothesSuggestion
}