package data.source.weather

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import data.source.weather.model.WeatherDto
import io.github.cdimascio.dotenv.dotenv
import org.baghdad.data.source.WeatherDataSource
import org.baghdad.logic.module.exceptions.ErrorFetchingWeatherData

class ApiWeatherDataSource(
    private val client: HttpClient,
) : WeatherDataSource {

    private val apiKey =
        dotenv()["OPEN_WEATHER_API_KEY"] ?: throw ErrorFetchingWeatherData("Missing OPEN_WEATHER_API_KEY")

    override suspend fun fetchCurrentWeather(city: String): WeatherDto {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", apiKey)
            parameter("units", "metric")
        }.body()
    }
}