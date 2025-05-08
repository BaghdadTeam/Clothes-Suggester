package data.source.weather

import data.source.weather.model.WeatherDto
import io.ktor.client.*
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
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
        val mockResponse = """
            {
                "main": {
                    "temp": 22.5,
                    "humidity": 60
                },
                "weather": [{
                    "main": "Clear",
                    "description": "clear sky"
                }],
                "wind": {
                    "speed": 5.5
                }
            }
        """.trimIndent()

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
        val result: WeatherDto = dataSource.fetchCurrentWeather("Baghdad")

        // Then
        assertEquals(22.5, result.main.temp)
        assertEquals(60, result.main.humidity)
        assertEquals("Clear", result.weather.first().main)
        assertEquals("clear sky", result.weather.first().description)
        assertEquals(5.5, result.wind.speed)
    }
}