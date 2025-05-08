package data.repository.clothes

import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import org.baghdad.data.clothes.ClothesDataSource
import org.baghdad.data.repository.clothes.ClothesRepositoryImpl
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.entities.WeatherCondition
import org.junit.jupiter.api.*

class ClothesRepositoryImplTest {
    private lateinit var repository: ClothesRepositoryImpl
    private lateinit var dataSource: ClothesDataSource

    @BeforeEach
    fun setUp() {
        dataSource = mockk<ClothesDataSource>(relaxed = true)
        repository = ClothesRepositoryImpl(dataSource)
    }

    @Test
    fun `Should return ClothesSuggestion get clothes by weather status`() {
        // Given
        coEvery { dataSource.getClothes(any(), any()) } returns listOf("test")
        // When
        val result = runBlocking {
            repository.getClothesByWeatherStatus(CurrentWeather(10.0, WeatherCondition.Clear))
        }
        // Then
        assert(result.clothes.isNotEmpty())
    }

    @Test
    fun `Should return ClothesSuggestion get clothes by weather status when no clothes found`() {
        // Given
        coEvery { dataSource.getClothes(any(), any()) } returns emptyList()
        // When
        val result = runBlocking {
            repository.getClothesByWeatherStatus(CurrentWeather(10.0, WeatherCondition.Overcast))
        }
        // Then
        assert(result.clothes.isEmpty())
    }
}