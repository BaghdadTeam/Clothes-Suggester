package org.baghdad.logic.module.entities

import java.util.UUID

data class Clothe(
    val id: UUID ,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val clothType:ClotheTypeBasedOnWeather
    )
enum class ClotheTypeBasedOnWeather {
    UltraHeavy,
    VeryHeavy,
    Heavy,
    Medium,
    Light,
    UltraLight,
    RainProof,
    WindProof,
    HeatResistant,
    SandProof,
    FogProof,
    Versatile
}