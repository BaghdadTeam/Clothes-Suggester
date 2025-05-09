package data.datasource.remote.weather

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import data.dto.WeatherDto
import io.github.cdimascio.dotenv.Dotenv
import data.datasource.WeatherDataSource
import org.baghdad.logic.module.exceptions.ErrorFetchingWeatherData

class ApiWeatherDataSource(
    private val client: HttpClient
) : WeatherDataSource {
    private val API_KEY =
        Dotenv.load()["API_KEY"] ?: throw ErrorFetchingWeatherData("Missing Api Key")

    override suspend fun fetchCurrentWeather(city: String): WeatherDto {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric")
        }.body()
    }
}