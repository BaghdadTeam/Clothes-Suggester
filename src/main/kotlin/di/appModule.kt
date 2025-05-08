package di

import WeatherCliApp
import org.baghdad.logic.usecase.GetOutfitUseCase
import org.baghdad.logic.usecase.GetWeatherUseCase
import org.baghdad.presentation.input.CliReader
import org.baghdad.presentation.output.CliLogger
import org.koin.dsl.module

val appModule = module {

    //------------------------------------UI ----------------------------------------
    single { CliReader() }
    single { CliLogger() }
    single { WeatherCliApp(get(), get()) }
    //------------------------------------UseCase------------------------------------
    single { GetWeatherUseCase() }
    single { GetOutfitUseCase() }


}