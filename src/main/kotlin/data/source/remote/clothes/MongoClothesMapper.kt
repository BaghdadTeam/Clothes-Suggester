package org.baghdad.data.source.remote.clothes

import org.baghdad.logic.module.entities.WeatherCondition

class MongoClothesMapper {
    fun mapTemperature(temperature: Double): String {
        return when {
            temperature < 0 -> "BELOW_0"
            temperature in 0.0..10.0 -> "ZERO_TO_10"
            temperature in 10.1..20.0 -> "TEN_TO_20"
            temperature in 20.1..30.0 -> "TWENTY_TO_30"
            else -> "ABOVE_30"
        }
    }

    fun mapCondition(weatherCondition: WeatherCondition): String {
        return when (weatherCondition) {
            WeatherCondition.Clear -> "CLEAR"
            WeatherCondition.Sunny -> "CLEAR"
            WeatherCondition.Cloudy -> "CLOUDY"
            WeatherCondition.Overcast -> "OVERCAST"
            WeatherCondition.Rainy -> "RAINY"
            WeatherCondition.Drizzle -> "DRIZZLE"
            WeatherCondition.Showers -> "SHOWERS"
            WeatherCondition.Snowy -> "SNOWY"
            WeatherCondition.Sleet -> "SLEET"
            WeatherCondition.Windy -> "WINDY"
            WeatherCondition.Stormy -> "STORMY"
            WeatherCondition.Foggy -> "FOGGY"
            WeatherCondition.Haze -> "HAZE"
            WeatherCondition.Dust -> "DUST"
            WeatherCondition.Mist -> "MIST"
            WeatherCondition.Unknown -> "UNKNOWN"
        }
    }
}

