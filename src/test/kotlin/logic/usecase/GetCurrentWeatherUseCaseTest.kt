package logic.usecase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.repository.WeatherRepository
import org.junit.jupiter.api.Test

class GetCurrentWeatherUseCaseTest {

 private val weatherRepository = mockk<WeatherRepository>()
 private val useCase = GetCurrentWeatherUseCase(weatherRepository)

 @Test
 fun `gets weather data for city`() = runTest {
  //given
  val expected = CurrentWeather(23f, WeatherCondition.Overcast)
  coEvery { weatherRepository.getCurrentWeatherByCityName("Cairo")
  } returns expected
  //when
  val result = useCase("Cairo")
  //then
  assertThat(result).isEqualTo(expected)
 }
}
