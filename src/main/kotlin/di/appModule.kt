package di
import GetCurrentWeatherUseCase
import logic.usecase.GetClothesSuggestionUseCase
import org.baghdad.data.clothes.ClothesDataSource
import org.baghdad.data.repository.clothes.ClothesRepositoryImpl
import org.baghdad.data.source.remote.clothes.MongoClientProvider
import org.baghdad.data.source.remote.clothes.MongoClothesDataSource
import org.baghdad.logic.repository.ClothesRepository
import org.baghdad.presentation.app.WeatherApp
import org.baghdad.presentation.input.CliReader
import org.baghdad.presentation.input.Reader
import org.baghdad.presentation.output.CliLogger
import org.baghdad.presentation.output.Logger
import org.koin.dsl.module

val appModule = module {
    single { GetCurrentWeatherUseCase(get()) }
    single { GetClothesSuggestionUseCase(get()) }
    single<Reader> { CliReader() }
    single<ClothesRepository> { ClothesRepositoryImpl(get()) }
    single { MongoClientProvider() }
    single<ClothesDataSource> { MongoClothesDataSource(get()) }
    single<Logger>{ CliLogger() }
    single { WeatherApp(get(), get(), get(), get()) }

}