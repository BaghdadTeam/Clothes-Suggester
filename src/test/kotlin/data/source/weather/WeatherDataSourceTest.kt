package data.source.weather

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class WeatherDataSourceTest {
    @Test
    fun `fetchCurrentWeather returns valid WeatherResponse on success`() = runTest {
        val jsonResponse = """
        {
            "main": { "temp": 25.0, "humidity": 60 },
            "weather": [{ "main": "Clear", "description": "clear sky" }],
            "wind": { "speed": 5.0 }
        }
    """.trimIndent()

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    assertEquals(
                        "https://api.openweathermap.org/data/2.5/weather",
                        request.url.toString().substringBefore("?")
                    )
                    respond(
                        content = jsonResponse,
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    )
                }
            }
            install(ContentNegotiation) {
                json()
            }
        }

        val dataSource = WeatherDataSource(client)
        val result = dataSource.fetchCurrentWeather("Cairo")

        assertEquals(25.0, result.main.temp)
        assertEquals("Clear", result.weather.first().main)
    }
}