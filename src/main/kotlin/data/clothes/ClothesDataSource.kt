package org.baghdad.data.clothes

import org.baghdad.logic.module.entities.WeatherCondition

interface ClothesDataSource {
    suspend fun getClothes(temperature: Double, weatherCondition: WeatherCondition): List<String>
}