package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.ClotheTypeBasedOnWeather
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.repository.ClothesRepository
import org.junit.jupiter.api.Test
import java.util.UUID

class GetClothesSuggestionUseCaseTest {

 private val clothesRepository = mockk<ClothesRepository>()
 private val useCase = GetClothesSuggestionUseCase(clothesRepository)

 @Test
 fun `returns clothes suggestion based on weather condition`() = runTest {
  // Given
  val weather = CurrentWeather(18.0, WeatherCondition.Sunny)
  // When
  coEvery { clothesRepository.getClothes() } returns listOf(expectedClothes)

  // And: the use case is invoked
  val result = useCase(weather)

  // Then
  assertThat(result).containsExactly(expectedClothes)
 }

 val expectedClothes = Clothe(
  id = UUID.randomUUID(),
  name = "Sweater",
  description = "A nice sweater",
  price = 10.0,
  imageUrl = "https://example.com/sweater.jpg",
  clothType = ClotheTypeBasedOnWeather.HeatResistant,
 )
}