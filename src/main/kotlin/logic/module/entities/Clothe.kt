package org.baghdad.logic.module.entities

import java.util.UUID

data class Cloth(
    val id: UUID ,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val clothType:ClotheTypeBasedOnWeather
    )
enum class ClotheTypeBasedOnWeather
{
    HeatResistant,
    Heavy,
    Light,
    UltraLight,
    UltraHeavy
}