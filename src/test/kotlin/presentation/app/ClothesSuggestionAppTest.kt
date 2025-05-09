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

        coEvery { reader.readInput() } returns cityName

        coEvery { weatherUseCase(cityName) } returns mockedWeather

        coEvery { clothesSuggestionUseCase(mockedWeather) } returns mockedClothes

        presenter.run()

        // Assert: Verify the interactions and outcomes
        coVerify { logger.info("Welcome to the weather app!") }
        coVerify { logger.info("Enter city name to get weather info") }
        coVerify { reader.readInput() }
        coVerify { logger.info("Weather for $cityName is $mockedWeather") }
        coVerify { logger.showOutfit(cityName, mockedWeather, mockedClothes) }
        coVerify { logger.info("Have a nice day!") }
    }

    @Test
    fun `should handle generic exceptions and show error message`() {
        val cityName = "Cairo"
        val errorMessage = "Unknown error occurred"

        coEvery { reader.readInput() } returns cityName
        coEvery { weatherUseCase(cityName) } throws Exception(errorMessage)

        runTest { presenter.run() }
        coVerify { logger.showError(errorMessage) }
    }
}