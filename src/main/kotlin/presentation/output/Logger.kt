package org.baghdad.presentation.output

import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather

interface Logger {
    fun info(message: String)
    fun showOutfit(location: String, weather: CurrentWeather, outfit: ClothesSuggestion)
    fun showError(message: String)
}