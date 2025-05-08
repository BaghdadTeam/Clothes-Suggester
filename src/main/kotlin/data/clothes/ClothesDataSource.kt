package org.baghdad.data.clothes

import org.baghdad.data.dto.ClotheDto
import org.baghdad.logic.module.entities.WeatherCondition

interface ClothesDataSource {
    suspend fun getClothes(): List<ClotheDto>
}