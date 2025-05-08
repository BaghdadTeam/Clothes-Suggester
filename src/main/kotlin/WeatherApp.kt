import org.baghdad.logic.usecase.GetOutfitUseCase
import org.baghdad.logic.usecase.GetWeatherUseCase
import org.baghdad.presentation.input.CliReader
import org.baghdad.presentation.output.CliLogger

class WeatherCliApp(
    private val weatherUseCase: GetWeatherUseCase,
    private val outfitUseCase: GetOutfitUseCase,
    private val reader: CliReader = CliReader(),
    private val logger: CliLogger = CliLogger()
) {
    private var temperatureUnit: String = "°C"
    private var stylePreference: String = "Casual"

    fun run() {
        while (true) {
            logger.showMainMenu()
            when (reader.readMenuChoice()) {
                1 -> handleTodayOutfit()
                2 -> handleForecast()
                3 -> handlePreferences()
                4 -> showHelp()
                5 -> exitApp()
                else -> logger.showError("Invalid choice")
            }
        }
    }

    private fun handleTodayOutfit() {
        try {
            val location = reader.readLocation()
            val weather = weatherUseCase.execute(location)
            val outfit = outfitUseCase.execute(weather)
            logger.showOutfit(location, weather, outfit)
        } catch (e: Exception) {
            logger.showError(e.message ?: "Failed to get outfit")
        }
        reader.waitForEnter()
    }

    private fun handleForecast() {
        logger.showError("Forecast feature coming soon!")
        reader.waitForEnter()
    }

    private fun handlePreferences() {
        while (true) {
            logger.showPreferences(temperatureUnit, stylePreference)
            when (reader.readMenuChoice()) {
                1 -> changeTemperatureUnit()
                2 -> changeStylePreference()
                3 -> return
                else -> logger.showError("Invalid choice")
            }
        }
    }

    private fun changeTemperatureUnit() {
        val newUnit = reader.readPreferenceInput("Enter unit (°C/°F):")
        temperatureUnit = if (newUnit.equals("°F", true)) "°F" else "°C"
    }

    private fun changeStylePreference() {
        stylePreference = reader.readPreferenceInput("Enter style (Casual/Formal/Sporty):")
    }

    private fun showHelp() {
        println(
            """
            ========================================
            ℹ️  Help:
            - Use city names or ZIP codes
            - Recommendations adapt to temperature
            - Change preferences in Settings
            ========================================
            """.trimIndent()
        )
        reader.waitForEnter()
    }

    private fun exitApp() {
        println("\nThanks for using WEARTHER! 👗👔")
        System.exit(0)
    }
}