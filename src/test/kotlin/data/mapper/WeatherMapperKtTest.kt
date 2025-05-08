package data.mapper

import com.google.common.truth.Truth.assertThat
import data.source.weather.model.Main
import data.source.weather.model.Weather
import data.source.weather.model.WeatherDto
import data.source.weather.model.Wind
import org.baghdad.data.mapper.toDomain
import org.baghdad.logic.module.entities.WeatherCondition
import kotlin.test.Test

class WeatherResponseMapperTest {
    @Test
    fun `should map temperature and clear weather condition correctly`() {
        val weatherResponse = WeatherDto(
            main = Main(temp = 25.0, humidity = 60),
            weather = listOf(Weather(main = "Clear", description = "clear sky")),
            wind = Wind(speed = 5.0)
        )

        val domainModel = weatherResponse.toDomain()

        assertThat(domainModel.temperatureInCelsius).isEqualTo(25.0)
        assertThat(domainModel.weatherCondition).isEqualTo(WeatherCondition.Clear)
    }

    @Test
    fun `should return Unknown for unrecognized description`() {
        val weatherResponse = WeatherDto(
            main = Main(temp = 15.0, humidity = 40),
            weather = listOf(Weather(main = "Alien", description = "strange phenomena")),
            wind = Wind(speed = 3.0)
        )

        val domainModel = weatherResponse.toDomain()

        assertThat(domainModel.weatherCondition).isEqualTo(WeatherCondition.Unknown)
    }

    @Test
    fun `should handle empty weather list`() {
        val weatherResponse = WeatherDto(
            main = Main(temp = 12.0, humidity = 30),
            weather = emptyList(),
            wind = Wind(speed = 2.0)
        )

        val domainModel = weatherResponse.toDomain()

        assertThat(domainModel.weatherCondition).isEqualTo(WeatherCondition.Unknown)
    }

    @Test
    fun `test empty weather list returns Unknown condition`() {
        val main = Main(temp = 18.0, humidity = 55)
        val wind = Wind(speed = 2.0)
        val weather = emptyList<Weather>()

        val response = WeatherDto(main, weather, wind)
        val domain = response.toDomain()

        assertThat(domain.weatherCondition).isEqualTo(WeatherCondition.Unknown)
    }
}