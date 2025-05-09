package org.baghdad.data.repository.clothes

import org.baghdad.data.clothes.ClothesDataSource
import org.baghdad.data.dto.toDomain
import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.repository.ClothesRepository

class ClothesRepositoryImpl(
    private val dataSource: ClothesDataSource,
) : ClothesRepository {
    override suspend fun getClothes(): List<Clothe> {
        return dataSource.getClothes().map { it.toDomain() }
    }
}