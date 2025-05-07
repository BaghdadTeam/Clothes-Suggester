package logic.usecase
import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition

class GetClothesSuggestionUseCase {
    operator fun invoke(weather: CurrentWeather): ClothesSuggestion {
        val temperature = weather.temperatureInCelsius
        val condition = weather.weatherCondition

        val suggestions = mutableListOf<String>()

        when {
            temperature < 0 -> suggestions.addAll(listOf("Heavy coat", "Gloves"))
            temperature in 0.0..10.0 -> suggestions.add("Jacket")
            temperature in 10.0..20.0 -> suggestions.add("Sweater")
            else -> suggestions.add("T-shirt")
        }

        if (condition == WeatherCondition.Rainy ||
            condition == WeatherCondition.Drizzle ||
            condition == WeatherCondition.Showers) {
            suggestions.add("Umbrella")
        }

        return ClothesSuggestion(clothes = suggestions)
    }
}
