package org.baghdad.logic.repository

import org.baghdad.data.dto.ClotheDto
import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.CurrentWeather


interface ClothesRepository {
    suspend fun getClothes(): List<Clothe>
}