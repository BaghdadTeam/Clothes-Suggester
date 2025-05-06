package di

import data.source.remote.wheather.WeatherRemoteSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.baghdad.logic.repository.WeatherRepositoryImpl
import org.baghdad.logic.repository.WeatherRepository
import org.koin.dsl.module

val appModule = module {

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
    single { WeatherRemoteSource(get()) }

    // Repositories
//    single { ClothesRepository() } // If it doesn't depend on external sources

    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

}