package logic.module.services

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.baghdad.logic.module.entities.ClotheTypeBasedOnWeather.*
import org.baghdad.logic.module.entities.WeatherCondition.*
import org.junit.jupiter.api.Test

class WeatherToClotheTypeMapperTest {

    @Test
    fun `should map temperature -10 to UltraHeavy and VeryHeavy`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(-10.0)
        result shouldContainExactlyInAnyOrder listOf(UltraHeavy, VeryHeavy)
    }

    @Test
    fun `should map temperature 8 to Heavy and Medium`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(8.0)
        result shouldContainExactlyInAnyOrder listOf(Heavy, Medium)
    }

    @Test
    fun `should map temperature 15 to Medium and Light`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(15.0)
        result shouldContainExactlyInAnyOrder listOf(Medium, Light)
    }

    @Test
    fun `should map condition Rainy to RainProof and HeatResistant`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Rainy)
        result shouldContainExactlyInAnyOrder listOf(RainProof, HeatResistant)
    }

    @Test
    fun `should map condition Foggy to FogProof and Versatile`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Foggy)
        result shouldContainExactlyInAnyOrder listOf(FogProof, Versatile)
    }

    @Test
    fun `should map condition Dust to SandProof and HeatResistant`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Dust)
        result shouldContainExactlyInAnyOrder listOf(SandProof, HeatResistant)
    }

    @Test
    fun `should combine temperature and condition mappings`() {
        val result = WeatherToClotheTypeMapper.mapToClotheTypes(30.0, Rainy)
        result shouldContainExactlyInAnyOrder listOf(UltraLight, HeatResistant, RainProof)
    }

    @Test
    fun `should remove duplicates in combined mapping`() {
        val result = WeatherToClotheTypeMapper.mapToClotheTypes(32.0, Dust)
        // Both temp and condition suggest HeatResistant, so it should only appear once
        result shouldContainExactlyInAnyOrder listOf(UltraLight, HeatResistant, SandProof)
    }
    @Test
    fun `should map temperature NaN to Versatile`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(Double.NaN)
        result shouldContainExactlyInAnyOrder listOf(Versatile)
    }

    @Test
    fun `should map unknown condition to Versatile`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Unknown)
        result shouldContainExactlyInAnyOrder listOf(Versatile)
    }
    @Test
    fun `should map temperature 0 to VeryHeavy and Heavy`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(0.0)
        result shouldContainExactlyInAnyOrder listOf(VeryHeavy, Heavy)
    }

    @Test
    fun `should map temperature 5 to VeryHeavy and Heavy`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(5.0)
        result shouldContainExactlyInAnyOrder listOf(VeryHeavy, Heavy)
    }

    @Test
    fun `should map temperature 12 to Heavy and Medium`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(12.0)
        result shouldContainExactlyInAnyOrder listOf(Heavy, Medium)
    }

    @Test
    fun `should map temperature 20 to Medium and Light`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(20.0)
        result shouldContainExactlyInAnyOrder listOf(Medium, Light)
    }

    @Test
    fun `should map temperature 28 to UltraLight and Light`() {
        val result = WeatherToClotheTypeMapper.mapTemperatureToClotheType(28.0)
        result shouldContainExactlyInAnyOrder listOf(UltraLight, Light)
    }

    @Test
    fun `should map condition Sunny to UltraLight and HeatResistant`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Sunny)
        result shouldContainExactlyInAnyOrder listOf(UltraLight, HeatResistant)
    }

    @Test
    fun `should map condition Cloudy to Light and Medium`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Cloudy)
        result shouldContainExactlyInAnyOrder listOf(Light, Medium)
    }

    @Test
    fun `should map condition Snowy to UltraHeavy and VeryHeavy`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Snowy)
        result shouldContainExactlyInAnyOrder listOf(UltraHeavy, VeryHeavy)
    }

    @Test
    fun `should map condition Windy to WindProof and Medium`() {
        val result = WeatherToClotheTypeMapper.mapConditionToClotheType(Windy)
        result shouldContainExactlyInAnyOrder listOf(WindProof, Medium)
    }

    @Test
    fun `should map extreme cold with snowy condition`() {
        val result = WeatherToClotheTypeMapper.mapToClotheTypes(-15.0, Snowy)
        result shouldContainExactlyInAnyOrder listOf(UltraHeavy, VeryHeavy)
    }

    @Test
    fun `should map warm with windy condition`() {
        val result = WeatherToClotheTypeMapper.mapToClotheTypes(18.0, Windy)
        result shouldContainExactlyInAnyOrder listOf(Medium, Light, WindProof)
    }

    @Test
    fun `should map hot with sunny condition`() {
        val result = WeatherToClotheTypeMapper.mapToClotheTypes(35.0, Sunny)
        result shouldContainExactlyInAnyOrder listOf(UltraLight, HeatResistant)
    }

    @Test
    fun `should map mild temperature with rainy condition`() {
        val result = WeatherToClotheTypeMapper.mapToClotheTypes(10.0, Rainy)
        result shouldContainExactlyInAnyOrder listOf(Heavy, Medium, RainProof, HeatResistant)
    }
}