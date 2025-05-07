package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.repository.ClothesRepository
import org.junit.jupiter.api.Test

class GetClothesSuggestionUseCaseTest {

 private val clothesRepository = mockk<ClothesRepository>()
 private val useCase = GetClothesSuggestionUseCase(clothesRepository)

 @Test
 fun `returns clothes suggestion from repository`() = runTest {
  val weather = CurrentWeather(18.0, WeatherCondition.Sunny)
  val expected = ClothesSuggestion(listOf("Sweater"))

  coEvery { clothesRepository.getClothesByWeatherStatus(weather) } returns expected

  val result = useCase(weather)

  assertThat(result).isEqualTo(expected)
 }
}
