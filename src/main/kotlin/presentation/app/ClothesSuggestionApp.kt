package org.baghdad.presentation.app

import GetCurrentWeatherUseCase
import logic.usecase.GetClothesSuggestionUseCase
import org.baghdad.logic.module.exceptions.NotValidCityNameException
import org.baghdad.presentation.input.Reader
import org.baghdad.presentation.output.Logger

class ClothesSuggestionApp(
    private val logger: Logger,
    private val weatherUseCase: GetCurrentWeatherUseCase,
    private val clothesSuggestionUseCase: GetClothesSuggestionUseCase,
    private val reader: Reader
) {
    suspend fun run() {
        logger.info("👋 Welcome to the Clothes Suggestion App!\n")
        try {
            logger.info("🌍 Please enter the name of your city to check the weather:")
            val cityName = reader.readInput()
            val weather = weatherUseCase(cityName)
            logger.info("📍 Weather in $cityName: $weather")
            val clothes = clothesSuggestionUseCase(weather)
            logger.showOutfit(cityName, weather, clothes)
            logger.info("🧥 Stay comfy and have a great day!")
        } catch (_: NotValidCityNameException) {
            logger.showError("⚠️ That doesn't seem like a valid city name. Please try again.")
            run()
        } catch (e: Exception) {
            logger.showError("❌ Oops! Something went wrong: ${e.message ?: "Unknown error"}")
        }
    }
}