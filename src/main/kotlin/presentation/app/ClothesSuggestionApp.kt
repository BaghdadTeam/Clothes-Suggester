package org.baghdad.presentation.app

import GetCurrentWeatherUseCase
import logic.usecase.GetClothesSuggestionUseCase
import org.baghdad.logic.module.exceptions.NotValidCityNameException
import org.baghdad.presentation.input.Reader
import org.baghdad.presentation.output.Logger

class WeatherApp(
    private val logger: Logger,
    private val weatherUseCase: GetCurrentWeatherUseCase,
    private val clothesSuggestionUseCase: GetClothesSuggestionUseCase,
    private val reader: Reader
) {
    suspend fun run() {
        logger.info("Welcome to the weather app!")
        try {
            logger.info("Enter city name to get weather info")
            val cityName = reader.readInput()
            val weather = weatherUseCase(cityName)
            logger.info("Weather for $cityName is $weather")
            val clothes = clothesSuggestionUseCase(weather)
            logger.showOutfit(cityName, weather, clothes)
            logger.info("Have a nice day!")
        } catch (_: NotValidCityNameException) {
            logger.showError("Please enter a valid city name")
            run()
        } catch (e: Exception) {
            logger.showError(e.message ?: "Unknown error")
        }

    }
}