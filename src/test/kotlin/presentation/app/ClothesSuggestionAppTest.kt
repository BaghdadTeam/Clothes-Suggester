package presentation.app

import GetCurrentWeatherUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.test.runTest
import logic.usecase.GetClothesSuggestionUseCase
import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.ClotheTypeBasedOnWeather
import org.baghdad.presentation.app.ClothesSuggestionApp
import org.baghdad.presentation.input.Reader
import org.baghdad.presentation.output.Logger
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.exceptions.NotValidCityNameException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class ClothesSuggestionAppTest {
    private lateinit var presenter: ClothesSuggestionApp
    private val reader: Reader = mockk()
    private val logger: Logger = mockk(relaxed = true)
    private val weatherUseCase: GetCurrentWeatherUseCase = mockk()
    private val clothesSuggestionUseCase: GetClothesSuggestionUseCase = mockk()

    @BeforeEach
    fun setUp() {
        presenter = ClothesSuggestionApp(logger, weatherUseCase, clothesSuggestionUseCase, reader)
    }

    @Test
    fun `should run app successfully with valid city name`() = runTest {
        // Given
        val cityName = "Cairo"
        val mockedWeather = CurrentWeather(30.0, WeatherCondition.Clear)
        val mockedClothes = listOf(
            Clothe(
                id = UUID.randomUUID(),
                name = "T-Shirt",
                description = "Light summer t-shirt",
                price = 15.99,
                imageUrl = "https://example.com/tshirt.jpg",
                clothType = ClotheTypeBasedOnWeather.HeatResistant,
            )
        )

        coEvery { reader.readInput() } returns cityName
        coEvery { weatherUseCase(cityName) } returns mockedWeather
        coEvery { clothesSuggestionUseCase(mockedWeather) } returns mockedClothes

        // When
        presenter.run()

        // Then
        verifyOrder {
            logger.info("👋 Welcome to the Clothes Suggestion App!\n")
            logger.info("🌍 Please enter the name of your city to check the weather (or 'exit' to quit):")
            reader.readInput()
            logger.info("📍 Weather in $cityName: $mockedWeather")
            logger.showOutfit(cityName, mockedWeather, mockedClothes)
            logger.info("🧥 Stay comfy and have a great day!")
        }
    }

    @Test
    fun `should handle invalid city name and prompt again`() = runTest {
        // Given
        val invalidCity = "InvalidCity123"
        val validCity = "Cairo"
        val mockedWeather = CurrentWeather(30.0, WeatherCondition.Clear)
        val mockedClothes = emptyList<Clothe>()

        coEvery { reader.readInput() } returnsMany listOf(invalidCity, validCity)
        coEvery { weatherUseCase(invalidCity) } throws NotValidCityNameException("City name can only contain letters and spaces")
        coEvery { weatherUseCase(validCity) } returns mockedWeather
        coEvery { clothesSuggestionUseCase(mockedWeather) } returns mockedClothes

        // When
        presenter.run()

        // Then
        verify(exactly = 2) { reader.readInput() }
        verify(exactly = 1) { logger.showError("⚠️ That doesn't seem like a valid city name. Please try again.") }
    }

    @Test
    fun `should handle generic exceptions and show error message`() = runTest {
        // Given
        val cityName = "Cairo"
        val errorMessage = "API unavailable"

        coEvery { reader.readInput() } returns cityName
        coEvery { weatherUseCase(cityName) } throws Exception(errorMessage)

        // When
        presenter.run()

        // Then
        verify {
            logger.showError("❌ Oops! Something went wrong: $errorMessage")
        }
        coVerify (exactly = 0) { clothesSuggestionUseCase(any()) }
    }

    @Test
    fun `should exit when user enters exit command`() = runTest {
        // Given
        coEvery { reader.readInput() } returns "exit"

        // When
        presenter.run()

        // Then
        verifyOrder {
            logger.info("👋 Welcome to the Clothes Suggestion App!\n")
            logger.info("🌍 Please enter the name of your city to check the weather (or 'exit' to quit):")
            reader.readInput()
            logger.info("👋 Goodbye!")
        }
        coVerify(exactly = 0) { weatherUseCase(any()) }
        coVerify(exactly = 0) { clothesSuggestionUseCase(any()) }
    }
}