package logic.module.services
import org.baghdad.logic.module.entities.ClotheTypeBasedOnWeather
import org.baghdad.logic.module.entities.WeatherCondition

object WeatherToClotheTypeMapper {
    fun mapTemperatureToClotheType(temp: Double): List<ClotheTypeBasedOnWeather> = when {
        temp < -5 -> listOf(ClotheTypeBasedOnWeather.UltraHeavy, ClotheTypeBasedOnWeather.VeryHeavy)
        temp in -5.0..5.0 -> listOf(ClotheTypeBasedOnWeather.VeryHeavy, ClotheTypeBasedOnWeather.Heavy)
        temp in 6.0..12.0 -> listOf(ClotheTypeBasedOnWeather.Heavy, ClotheTypeBasedOnWeather.Medium)
        temp in 13.0..20.0 -> listOf(ClotheTypeBasedOnWeather.Medium, ClotheTypeBasedOnWeather.Light)
        temp in 21.0..28.0 -> listOf(ClotheTypeBasedOnWeather.UltraLight, ClotheTypeBasedOnWeather.Light)
        temp > 28 -> listOf(ClotheTypeBasedOnWeather.UltraLight, ClotheTypeBasedOnWeather.HeatResistant)
        else -> listOf(ClotheTypeBasedOnWeather.Versatile)
    }

    fun mapConditionToClotheType(condition: WeatherCondition): List<ClotheTypeBasedOnWeather> = when (condition) {
        WeatherCondition.Clear, WeatherCondition.Sunny ->
            listOf(ClotheTypeBasedOnWeather.UltraLight, ClotheTypeBasedOnWeather.HeatResistant)

        WeatherCondition.Cloudy, WeatherCondition.Overcast ->
            listOf(ClotheTypeBasedOnWeather.Light, ClotheTypeBasedOnWeather.Medium)

        WeatherCondition.Rainy, WeatherCondition.Drizzle, WeatherCondition.Showers, WeatherCondition.Mist ->
            listOf(ClotheTypeBasedOnWeather.RainProof, ClotheTypeBasedOnWeather.HeatResistant)

        WeatherCondition.Snowy, WeatherCondition.Sleet ->
            listOf(ClotheTypeBasedOnWeather.UltraHeavy, ClotheTypeBasedOnWeather.VeryHeavy)

        WeatherCondition.Stormy ->
            listOf(ClotheTypeBasedOnWeather.RainProof, ClotheTypeBasedOnWeather.VeryHeavy)

        WeatherCondition.Windy ->
            listOf(ClotheTypeBasedOnWeather.WindProof, ClotheTypeBasedOnWeather.Medium)

        WeatherCondition.Foggy, WeatherCondition.Haze ->
            listOf(ClotheTypeBasedOnWeather.FogProof, ClotheTypeBasedOnWeather.Versatile)

        WeatherCondition.Dust ->
            listOf(ClotheTypeBasedOnWeather.SandProof, ClotheTypeBasedOnWeather.HeatResistant)

        WeatherCondition.Unknown ->
            listOf(ClotheTypeBasedOnWeather.Versatile)
    }

    fun mapToClotheTypes(temp: Double, condition: WeatherCondition): List<ClotheTypeBasedOnWeather> =
        (mapTemperatureToClotheType(temp) + mapConditionToClotheType(condition)).distinct()
}