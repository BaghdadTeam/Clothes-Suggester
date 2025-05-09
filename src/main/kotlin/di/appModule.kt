package di
import GetCurrentWeatherUseCase
import data.source.remote.weather.ApiWeatherDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import logic.usecase.GetClothesSuggestionUseCase
import org.baghdad.data.clothes.ClothesDataSource
import org.baghdad.data.repository.clothes.ClothesRepositoryImpl
import org.baghdad.data.repository.weather.WeatherRepositoryImpl
import org.baghdad.data.weather.WeatherDataSource
import org.baghdad.data.source.remote.clothes.MongoClientProvider
import org.baghdad.data.source.remote.clothes.MongoClothesDataSource
import org.baghdad.logic.repository.ClothesRepository
import org.baghdad.logic.repository.WeatherRepository
import org.baghdad.presentation.app.ClothesSuggestionApp
import org.baghdad.presentation.input.CliReader
import org.baghdad.presentation.input.Reader
import org.baghdad.presentation.output.CliLogger
import org.baghdad.presentation.output.Logger
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // Data source
    single<WeatherDataSource> { ApiWeatherDataSource(get()) }
    single { MongoClientProvider() }
    single<ClothesDataSource>{ MongoClothesDataSource(get()) }
    //Repository
    single<ClothesRepository>{ ClothesRepositoryImpl(get()) }
    single<WeatherRepository>{ WeatherRepositoryImpl(get()) }

    // UseCases
    single {GetCurrentWeatherUseCase(get())}
    single{ GetClothesSuggestionUseCase(get()) }

    // UI
    single<Reader>{ CliReader() }
    single<Logger>{ CliLogger() }
    single { ClothesSuggestionApp(get(), get(), get(), get()) }


}