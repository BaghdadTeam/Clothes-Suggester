package org.baghdad.presentation.output

import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather


class CliLogger : Logger {
    override fun info(message: String) {
        println("[INFO] $message")
    }

    override fun showOutfit(location: String, weather: CurrentWeather, outfit: ClothesSuggestion) {
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

    override fun showError(message: String) {
        System.err.println("[ERROR] $message")
    }
}