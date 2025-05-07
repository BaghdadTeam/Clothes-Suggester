package logic.usecase
import com.google.common.truth.Truth.assertThat
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition
import org.junit.jupiter.api.Test

class GetClothesSuggestionUseCaseTest {

 private val useCase = GetClothesSuggestionUseCase()

 @Test
 fun `suggest clothes for subzero snowy weather`() {
  //given
  val weather = CurrentWeather(-5f, WeatherCondition.Snowy)
  //when
  val result = useCase(weather)
  //then
  assertThat(result.clothes).containsExactly("Heavy coat", "Gloves")
 }

 @Test
 fun `suggest clothes for chilly rainy weather`() {
  //given
  val weather = CurrentWeather(5f, WeatherCondition.Rainy)
  //when
  val result = useCase(weather)
  //then
  assertThat(result.clothes).containsExactly("Jacket", "Umbrella")
 }

 @Test
 fun `suggest clothes for mild weather`() {
  //given
  val weather = CurrentWeather(15f, WeatherCondition.Clear)
  //when
  val result = useCase(weather)
  //then
  assertThat(result.clothes).containsExactly("Sweater")
 }

 @Test
 fun `suggest clothes for hot drizzle weather`() {
  //given
  val weather = CurrentWeather(30f, WeatherCondition.Drizzle)
  //when
  val result = useCase(weather)
  //then
  assertThat(result.clothes).containsExactly("T-shirt", "Umbrella")
 }
 @Test
 fun `suggest clothes for hot showers weather`() {
  //given
  val weather = CurrentWeather(30f, WeatherCondition.Showers)
  //when
  val result = useCase(weather)
  //then
  assertThat(result.clothes).containsExactly("T-shirt", "Umbrella")
 }

 @Test
 fun `suggest clothes for hot sunny weather`() {
  //given
  val weather = CurrentWeather(35f, WeatherCondition.Sunny)
  //when
  val result = useCase(weather)
  //then
  assertThat(result.clothes).containsExactly("T-shirt")
 }
}