package org.baghdad

import WeatherCliApp
import di.appModule
import org.baghdad.logic.usecase.GetOutfitUseCase
import org.baghdad.logic.usecase.GetWeatherUseCase
import org.koin.core.context.GlobalContext.startKoin


fun main() {
    // Initialize DI
    startKoin { modules(appModule) }

    // Start CLI
    WeatherCliApp(weatherUseCase = GetWeatherUseCase(), outfitUseCase = GetOutfitUseCase()).run()

}

