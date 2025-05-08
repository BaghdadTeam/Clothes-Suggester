package data.source.weather

import data.source.weather.model.WeatherDto
import data.source.weather.model.Main
import data.source.weather.model.Weather
import data.source.weather.model.Wind
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ApiWeatherDataSourceTest {

    @Test
    fun `should return WeatherDto when API call is successful`() = runBlocking {
        // Given
        val expectedWeatherDto = WeatherDto(
            main = Main(temp = 22.5, humidity = 60),
            weather = listOf(Weather(main = "Clear", description = "clear sky")),
            wind = Wind(speed = 5.5)
        )
        val mockResponse = Json.encodeToString(WeatherDto.serializer(), expectedWeatherDto)

        val mockEngine = MockEngine { request ->
            respond(
                content = mockResponse,
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        val dataSource = ApiWeatherDataSource(client)
        // When
        val result: WeatherDto = dataSource.fetchCurrentWeather("AnyCity")
        // Then
        assertEquals(expectedWeatherDto.main.temp, result.main.temp)
        assertEquals(expectedWeatherDto.main.humidity, result.main.humidity)
        assertEquals(expectedWeatherDto.weather.first().main, result.weather.first().main)
        assertEquals(expectedWeatherDto.weather.first().description, result.weather.first().description)
        assertEquals(expectedWeatherDto.wind.speed, result.wind.speed)

    }
}