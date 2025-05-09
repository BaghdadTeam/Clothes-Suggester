package data.repository.weather

import com.google.common.truth.Truth.assertThat
import data.source.weather.ApiWeatherDataSource
import data.source.weather.model.Main
import data.source.weather.model.Weather
import data.source.weather.model.WeatherDto
import data.source.weather.model.Wind
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.baghdad.data.repository.weather.WeatherRepositoryImpl
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.module.exceptions.ErrorFetchingWeatherData
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class WeatherRepositoryImplTest {
    private val mockDataSource = mockk<ApiWeatherDataSource>(relaxed = true)
    private val repository = WeatherRepositoryImpl(mockDataSource)

    @Test
    fun `should return CurrentWeather with correct domain mapping`() = runTest {
        val city = "Cairo"
        val mockResponse = WeatherDto(
            main = Main(temp = 30.0, humidity = 50),
            weather = listOf(Weather(main = "Clear", description = "clear sky")),
            wind = Wind(speed = 10.0)
        )

        coEvery { mockDataSource.fetchCurrentWeather(city) } returns mockResponse

        val result = repository.getCurrentWeatherByCityName(city)

        assertThat(result.temperatureInCelsius).isEqualTo(30.0)
        assertThat(result.weatherCondition).isEqualTo(WeatherCondition.Clear)
    }

    @Test
    fun `should return Unknown when description is not recognized`() = runTest {
        val city = "UnknownCity"
        val mockResponse = WeatherDto(
            main = Main(temp = 20.0, humidity = 60),
            weather = listOf(Weather(main = "Something", description = "weird bubbles")),
            wind = Wind(speed = 5.0)
        )

        coEvery { mockDataSource.fetchCurrentWeather(city) } returns mockResponse

        val result = repository.getCurrentWeatherByCityName(city)
        assertThat(result.weatherCondition).isEqualTo(WeatherCondition.Unknown)
    }

    @Test
    fun `should return default weather when data source throws exception`() = runTest {
        val city = "InvalidCity"
        coEvery { mockDataSource.fetchCurrentWeather(city) } throws ErrorFetchingWeatherData("Failed to fetch weather data")
        assertThrows<ErrorFetchingWeatherData> { repository.getCurrentWeatherByCityName(city) }

    }
}