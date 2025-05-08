package data.source.weather

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import data.source.weather.model.WeatherDto
import io.github.cdimascio.dotenv.Dotenv
import org.baghdad.data.source.WeatherDataSource
import org.baghdad.logic.module.exceptions.ErrorFetchingWeatherData

class ApiWeatherDataSource(
    private val client: HttpClient
) : WeatherDataSource {
    val dotenv = Dotenv.load()
    val API_KEY = dotenv["API_KEY"] ?: throw ErrorFetchingWeatherData("Missing API_KEY")

    override suspend fun fetchCurrentWeather(city: String): WeatherDto {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric")
        }.body()
    }
}