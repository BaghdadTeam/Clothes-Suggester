package org.baghdad.data.dto

import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.ClotheTypeBasedOnWeather
import java.util.UUID

data class MongoClothesDocument(
    val clothes: List<ClotheDto>
)
data class ClotheDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val clothType: String
)

fun ClotheDto.toDomain(): Clothe = Clothe(
    id = UUID.fromString(this.id),
    name = this.name,
    description = this.description,
    price = this.price,
    imageUrl = this.imageUrl,
    clothType = ClotheTypeBasedOnWeather.valueOf(this.clothType)
)