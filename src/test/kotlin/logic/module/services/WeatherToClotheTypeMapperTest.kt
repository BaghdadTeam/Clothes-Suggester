import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import logic.module.services.WeatherToClotheTypeMapper
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
}