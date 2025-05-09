package presentation.app

import GetCurrentWeatherUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.usecase.GetClothesSuggestionUseCase
import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.ClotheTypeBasedOnWeather
import org.baghdad.presentation.app.ClothesSuggestionApp
import org.baghdad.presentation.input.Reader
import org.baghdad.presentation.output.Logger
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.module.entities.CurrentWeather
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class ClothesSuggestionAppTest  {

    lateinit var presenter: ClothesSuggestionApp
    private val reader: Reader = mockk()
    private val logger: Logger = mockk(relaxed = true)
    private val weatherUseCase: GetCurrentWeatherUseCase = mockk()
    private val clothesSuggestionUseCase: GetClothesSuggestionUseCase = mockk()

    @BeforeEach
    fun setUp() {
        presenter = ClothesSuggestionApp(logger, weatherUseCase, clothesSuggestionUseCase, reader)
    }

    @Test
    fun `should run app and suggest clothes based on weather`() = runTest {

        // Given
        val cityName = "Cairo"
        val mockedWeather = CurrentWeather(30.0, WeatherCondition.Clear)
        val mockedClothes = listOf(Clothe(
            id = UUID.randomUUID(),
            name = "Sweater",
            description = "A nice sweater with a nice hat on it.",
            price = 2.1,
            imageUrl = "https://example.com/sweater.jpg",
            clothType = ClotheTypeBasedOnWeather.HeatResistant,
        ))

        // When
        coEvery { reader.readInput() } returns cityName
        coEvery { weatherUseCase(cityName) } returns mockedWeather
        coEvery { clothesSuggestionUseCase(mockedWeather) } returns mockedClothes

        presenter.run()

        // Then
        coVerify { logger.info(any()) }
        coVerify { logger.info(any()) }
        coVerify { reader.readInput() }
        coVerify { logger.info(any()) }
        coVerify { logger.showOutfit(cityName, mockedWeather, mockedClothes) }
        coVerify { logger.info(any()) }
    }

    @Test
    fun `should handle generic exceptions and show error message`() {
        // Given
        val cityName = "Cairo"
        val errorMessage = "❌ Oops! Something went wrong: Unknown error occurred"
        // When
        coEvery { reader.readInput() } returns cityName
        coEvery { weatherUseCase(cityName) } throws Exception(errorMessage)
        // Then
        runTest { presenter.run() }
        coVerify { logger.showError(any())}
    }
}