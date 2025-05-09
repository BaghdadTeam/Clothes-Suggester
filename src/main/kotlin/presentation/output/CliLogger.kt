package org.baghdad.presentation.output

import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.CurrentWeather

class CliLogger : Logger {

    override fun info(message: String) {
        print(message)
    }

    override fun showOutfit(
        location: String,
        weather: CurrentWeather,
        outfit: List<Clothe>
    ) {
        println("""
            ========================================
            📍 $location | 🌡️ ${weather.temperatureInCelsius}°C | ${weather.weatherCondition}
            ========================================
            👕 Recommended Outfit:
        """.trimIndent())

        outfit.forEach { clothe ->
            println("- ${clothe.name}: ${clothe.description}")
        }
    }

    override fun showError(message: String) {
        System.err.println("[ERROR] $message")
    }
}