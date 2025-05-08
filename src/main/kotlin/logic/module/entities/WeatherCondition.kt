package org.baghdad.logic.module.entities

enum class WeatherCondition {
    Clear,
    Cloudy,
    Overcast,
    Rainy,
    Drizzle,
    Showers,
    Snowy,
    Sleet,
    Windy,
    Stormy,
    Foggy,
    Haze,
    Dust,
    Mist,
    Sunny,
    Unknown;
    companion object {
        fun fromDescription(description: String): WeatherCondition {
            val desc = description.lowercase()

            return when {
                "clear" in desc -> Clear
                "sun" in desc -> Sunny
                "cloud" in desc -> Cloudy
                "overcast" in desc -> Overcast
                "rain" in desc -> Rainy
                "drizzle" in desc -> Drizzle
                "shower" in desc -> Showers
                "snow" in desc -> Snowy
                "sleet" in desc -> Sleet
                "storm" in desc || "thunder" in desc -> Stormy
                "wind" in desc -> Windy
                "fog" in desc -> Foggy
                "haze" in desc -> Haze
                "dust" in desc -> Dust
                "mist" in desc -> Mist
                else -> Unknown
            }
        }
    }

}