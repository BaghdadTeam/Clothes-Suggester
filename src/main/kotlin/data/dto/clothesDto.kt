package org.baghdad.data.dto

data class ClothesDto(
    val temperature: Map<String, InnerClothesDto>,
    val condition: Map<String, InnerClothesDto>
)

data class InnerClothesDto(
    val clothes: List<String>
)
