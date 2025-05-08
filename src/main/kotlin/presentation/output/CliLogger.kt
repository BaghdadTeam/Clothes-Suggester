package org.baghdad.presentation.output

import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition


class CliLogger : Logger {
    override fun info(message: String) {
        println("[INFO] $message")
    }

    fun showMainMenu() {
        info(
            """
            ========================================
                  🌤️  WEARTHER - Outfit Advisor
            ========================================
            1. Get Today's Outfit Recommendation
            2. Get 3-Day Forecast Outfits
            3. Change Preferences
            4. Help
            5. Exit
            ========================================
            """.trimIndent()
        )
    }

    fun showOutfit(location: String, weather: CurrentWeather, outfit: ClothesSuggestion) {
        info(
            """
            ========================================
            📍 $location | 🌡️ ${weather.temperatureInCelsius}°C | ${weather.weatherCondition}
            ========================================
            👕 Recommended Outfit:
            ${outfit.clothes.joinToString("\n- ", "- ")}
            ========================================
            """.trimIndent()
        )
    }

    fun showPreferences(currentUnit: String, currentStyle: String) {
        println(
            """
            ========================================
            ⚙️ Current Preferences:
            - Temperature Unit: $currentUnit
            - Style: $currentStyle
            ========================================
            1. Change Temperature Unit
            2. Change Style Preference
            3. Back to Main Menu
            ========================================
            """.trimIndent()
        )
    }


    fun showError(message: String) {
        System.err.println("[ERROR] $message")
    }
}