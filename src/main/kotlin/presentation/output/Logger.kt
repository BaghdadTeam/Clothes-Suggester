package org.baghdad.presentation.output

import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.CurrentWeather

interface Logger {
    fun info(message: String)
    fun showOutfit(location: String, weather: CurrentWeather, outfit: List<Clothe>)
    fun showError(message: String)
}