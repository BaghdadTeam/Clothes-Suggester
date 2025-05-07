package data.source.weather

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import data.source.weather.model.WeatherResponse
import io.github.cdimascio.dotenv.dotenv
import org.baghdad.data.source.DataSource

class WeatherDataSource(private val client: HttpClient) : DataSource {
    companion object {
        val API_KEY = dotenv()["API_KEY"] ?: error("API key not found in .env")
    }

    override suspend fun fetchCurrentWeather(city: String): WeatherResponse {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric")
        }.body()
    }
}