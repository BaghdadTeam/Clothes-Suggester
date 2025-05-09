package org.baghdad.data.clothes

import org.baghdad.data.dto.ClotheDto
interface ClothesDataSource {
    suspend fun getClothes(): List<ClotheDto>
}