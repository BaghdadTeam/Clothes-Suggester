import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.exceptions.NotValidCityNameException
import org.baghdad.logic.repository.WeatherRepository
import org.baghdad.logic.module.entities.WeatherCondition

class GetCurrentWeatherUseCaseTest {
 private val weatherRepository = mockk<WeatherRepository>()
 private val useCase = GetCurrentWeatherUseCase(weatherRepository)

 @Test
 fun `valid city name should return current weather`() = runTest {
  val cityName = "Cairo"
  //given
  val expectedWeather = CurrentWeather(25.0, WeatherCondition.Clear)
  coEvery { weatherRepository.getCurrentWeatherByCityName(cityName)
  } returns expectedWeather
   //when
  val result = useCase(cityName)
  // then
  assertThat(result).isEqualTo(expectedWeather)
 }

 @Test
 fun `city name with numbers or symbols should throw NotValidCityNameException`() {
  val cityName = "Cai!ro123"
  //given
  coEvery { weatherRepository.getCurrentWeatherByCityName(cityName)
  } throws NotValidCityNameException("City name can only contain letters and spaces")
  // when
  val exception = assertThrows<NotValidCityNameException> {
   runTest { useCase(cityName) }
  }
  // then
  assertThat(exception).hasMessageThat()
   .contains("City name can only contain letters and spaces")
 }

 @Test
 fun `empty city name should throw NotValidCityNameException`() {
  val cityName = ""
  // given
  coEvery { weatherRepository.getCurrentWeatherByCityName(cityName)
  } throws NotValidCityNameException("City name can not be empty")
  // when
  val exception = assertThrows<NotValidCityNameException> {
   runTest { useCase(cityName) }
  }
  // then
  assertThat(exception).hasMessageThat()
   .contains("City name can not be empty")
 }

 @Test
 fun `city name with only spaces should throw NotValidCityNameException`() {
  val cityName = "   "
  // given
  coEvery { weatherRepository.getCurrentWeatherByCityName(cityName)
  } throws NotValidCityNameException("City name can not be empty")
  // when
  val exception = assertThrows<NotValidCityNameException> {
   runTest { useCase(cityName) }
  }
  // then
  assertThat(exception).hasMessageThat()
   .contains("City name can not be empty")
 }
}
