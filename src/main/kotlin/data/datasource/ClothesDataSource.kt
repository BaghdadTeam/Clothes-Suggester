package data.datasource

import org.baghdad.data.dto.ClotheDto

interface ClothesDataSource {
    suspend fun getClothes(): List<ClotheDto>
}