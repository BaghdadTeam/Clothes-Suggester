package data.source.remote.weather

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import data.source.remote.weather.model.WeatherResponse
import io.github.cdimascio.dotenv.dotenv

class WeatherRemoteSource(private val client: HttpClient) {
    companion object {
        val API_KEY = dotenv()["API_KEY"] ?: error("API key not found in .env")
    }

    suspend fun fetchCurrentWeather(city: String): WeatherResponse {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric")
        }.body()
    }
}