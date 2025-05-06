package data.source.remote.wheather

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import data.source.remote.wheather.model.OpenWeatherResponse

class WeatherRemoteSource(private val client: HttpClient) {
val API_KEY= "f7d8ba794e336a3dfe9983ddfa15f69d"
    suspend fun fetchCurrentWeather(city: String): OpenWeatherResponse {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric") // Celsius
        }.body()
    }
}