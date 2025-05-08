package org.baghdad.data.repository.clothes

import org.baghdad.data.clothes.ClothesDataSource
import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.repository.ClothesRepository

class ClothesRepositoryImpl(
    private val dataSource: ClothesDataSource
) : ClothesRepository {
    override suspend fun getClothesByWeatherStatus(currentWeather: CurrentWeather): ClothesSuggestion {
        val clothes = dataSource.getClothes(
            currentWeather.temperatureInCelsius, currentWeather.weatherCondition
        )
        return ClothesSuggestion(clothes)
    }
}