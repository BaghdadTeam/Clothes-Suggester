package di

import data.source.weather.ApiWeatherDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.baghdad.data.repository.clothes.ClothesRepositoryImpl
import org.baghdad.data.repository.weather.WeatherRepositoryImpl
import org.baghdad.data.source.WeatherDataSource
import org.baghdad.data.source.remote.clothes.MongoClientProvider
import org.baghdad.data.source.remote.clothes.MongoClothesDataSource
import org.baghdad.data.source.remote.clothes.MongoClothesMapper
import org.baghdad.logic.repository.WeatherRepository
import org.koin.dsl.module

val appModule = module {
    single { MongoClientProvider() }
    single { MongoClothesMapper() }
    single { MongoClothesDataSource(get(), get()) }
    single { ClothesRepositoryImpl(get()) }

    // Ktor HTTP Client with JSON serialization
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

    // Remote data source
    single<WeatherDataSource> { ApiWeatherDataSource(get()) }

    // Repositories
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

}