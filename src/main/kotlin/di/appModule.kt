package di

import GetCurrentWeatherUseCase
import logic.usecase.GetClothesSuggestionUseCase
import org.baghdad.presentation.app.WeatherApp
import org.baghdad.presentation.input.CliReader
import org.baghdad.presentation.input.Reader
import org.baghdad.presentation.output.CliLogger
import org.baghdad.presentation.output.Logger
import org.koin.dsl.module

val appModule = module {

    //------------------------------------UI ----------------------------------------
    single<Reader> { CliReader() }
    single<Logger> { CliLogger() }
    single { WeatherApp(get(), get(), get(), get()) }
    //------------------------------------UseCase------------------------------------
    single { GetCurrentWeatherUseCase(get()) }
    single { GetClothesSuggestionUseCase(get()) }
    //------------------------------------Repository------------------------------------
}