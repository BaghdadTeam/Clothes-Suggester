package data.source.remote.clothes
import org.baghdad.data.source.remote.clothes.MongoClothesMapper
import org.baghdad.logic.module.entities.WeatherCondition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MongoClothesMapperTest {
    lateinit var mapper: MongoClothesMapper
    @BeforeEach
    fun setup() {
        mapper = MongoClothesMapper()
    }

    @Test
    fun `should map temperature below 0`() {
        assertEquals("BELOW_0", mapper.mapTemperature(-5.0))
        assertEquals("BELOW_0", mapper.mapTemperature(-1.0))
    }

    @Test
    fun `should map temperature from 0 to 10`() {
        assertEquals("ZERO_TO_10", mapper.mapTemperature(0.0))
        assertEquals("ZERO_TO_10", mapper.mapTemperature(5.5))
        assertEquals("ZERO_TO_10", mapper.mapTemperature(10.0))
    }

    @Test
    fun `should map temperature from 10_1 to 20`() {
        assertEquals("TEN_TO_20", mapper.mapTemperature(10.1))
        assertEquals("TEN_TO_20", mapper.mapTemperature(15.0))
        assertEquals("TEN_TO_20", mapper.mapTemperature(20.0))
    }

    @Test
    fun `should map temperature from 20_1 to 30`() {
        assertEquals("TWENTY_TO_30", mapper.mapTemperature(20.1))
        assertEquals("TWENTY_TO_30", mapper.mapTemperature(25.0))
        assertEquals("TWENTY_TO_30", mapper.mapTemperature(30.0))
    }

    @Test
    fun `should map temperature above 30`() {
        assertEquals("ABOVE_30", mapper.mapTemperature(30.1))
        assertEquals("ABOVE_30", mapper.mapTemperature(40.0))
    }

    @Test
    fun `should map all weather conditions correctly`() {
        assertEquals("CLEAR", mapper.mapCondition(WeatherCondition.Clear))
        assertEquals("CLEAR", mapper.mapCondition(WeatherCondition.Sunny))
        assertEquals("CLOUDY", mapper.mapCondition(WeatherCondition.Cloudy))
        assertEquals("OVERCAST", mapper.mapCondition(WeatherCondition.Overcast))
        assertEquals("RAINY", mapper.mapCondition(WeatherCondition.Rainy))
        assertEquals("DRIZZLE", mapper.mapCondition(WeatherCondition.Drizzle))
        assertEquals("SHOWERS", mapper.mapCondition(WeatherCondition.Showers))
        assertEquals("SNOWY", mapper.mapCondition(WeatherCondition.Snowy))
        assertEquals("SLEET", mapper.mapCondition(WeatherCondition.Sleet))
        assertEquals("WINDY", mapper.mapCondition(WeatherCondition.Windy))
        assertEquals("STORMY", mapper.mapCondition(WeatherCondition.Stormy))
        assertEquals("FOGGY", mapper.mapCondition(WeatherCondition.Foggy))
        assertEquals("HAZE", mapper.mapCondition(WeatherCondition.Haze))
        assertEquals("DUST", mapper.mapCondition(WeatherCondition.Dust))
        assertEquals("MIST", mapper.mapCondition(WeatherCondition.Mist))
        assertEquals("UNKNOWN", mapper.mapCondition(WeatherCondition.Unknown))
    }
}
