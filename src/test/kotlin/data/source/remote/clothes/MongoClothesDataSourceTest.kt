package data.source.remote.clothes

import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.test.runTest
import org.baghdad.data.dto.ClothesDto
import org.baghdad.data.dto.InnerClothesDto
import org.baghdad.data.source.remote.clothes.MongoClientProvider
import org.baghdad.data.source.remote.clothes.MongoClothesDataSource
import org.baghdad.data.source.remote.clothes.MongoClothesMapper
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.module.exceptions.NoClothesSuggestionException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoClothesDataSourceTest {

    private lateinit var dataSource: MongoClothesDataSource
    private lateinit var mockClientProvider: MongoClientProvider
    private lateinit var mockClient: MongoClient
    private lateinit var mockMapper: MongoClothesMapper
    private lateinit var mockDatabase: MongoDatabase
    private lateinit var mockCollection: MongoCollection<ClothesDto>
    private lateinit var mockFindFlow: FindFlow<ClothesDto>

    @BeforeEach
    fun setup() {
        mockClientProvider = mockk(relaxed = true)
        mockClient = mockk(relaxed = true)
        mockMapper = mockk(relaxed = true)
        mockDatabase = mockk(relaxed = true)
        mockCollection = mockk(relaxed = true)
        mockFindFlow = mockk(relaxed = true)

        every { mockClientProvider.getClient() } returns mockClient
        every { mockClient.getDatabase(any()) } returns mockDatabase
        every { mockDatabase.getCollection<ClothesDto>(any()) } returns mockCollection

        dataSource = MongoClothesDataSource(mockClientProvider, mockMapper)
    }

    @Test
    fun `getClothes should return combined clothes for temperature and condition`() = runTest {
        // Given
        val testClothesDto = ClothesDto(
            temperature = mapOf("COLD" to InnerClothesDto(listOf("Coat", "Gloves"))),
            condition = mapOf("RAINY" to InnerClothesDto(listOf("Umbrella", "Rain Boots")))
        )


        coEvery { mockFindFlow.collect(any()) } coAnswers {
            val collector = arg<FlowCollector<ClothesDto>>(0)
            collector.emit(testClothesDto)
        }

        coEvery { mockCollection.find() } returns mockFindFlow


        coEvery { mockMapper.mapTemperature(5.0) } returns "COLD"
        coEvery { mockMapper.mapCondition(WeatherCondition.Rainy) } returns "RAINY"

        // When
        val result = dataSource.getClothes(5.0, WeatherCondition.Rainy)

        // Then: Should return the combined clothes
        assertEquals(4, result.size)
        assertTrue(result.contains("Coat"))
        assertTrue(result.contains("Gloves"))
        assertTrue(result.contains("Umbrella"))
        assertTrue(result.contains("Rain Boots"))

        verify { mockClientProvider.getClient() }
        verify { mockClient.getDatabase("clothes-db") }
        verify { mockDatabase.getCollection<ClothesDto>("clothes") }
        coVerify { mockCollection.find() }
        coVerify { mockMapper.mapTemperature(5.0) }
        coVerify { mockMapper.mapCondition(WeatherCondition.Rainy) }
    }

    @Test
    fun `getClothes should return only temperature clothes when no condition clothes found`() = runTest {
        // Given
        val testClothesDto = ClothesDto(
            temperature = mapOf("HOT" to InnerClothesDto(listOf("T-shirt", "Shorts"))),
            condition = mapOf("RAINY" to InnerClothesDto(listOf("Umbrella")))
        )


        coEvery { mockFindFlow.collect(any()) } coAnswers {
            val collector = arg<FlowCollector<ClothesDto>>(0)
            collector.emit(testClothesDto)
        }

        coEvery { mockCollection.find() } returns mockFindFlow

        coEvery { mockMapper.mapTemperature(30.0) } returns "HOT"
        coEvery { mockMapper.mapCondition(WeatherCondition.Clear) } returns "CLEAR"

        // When & Then
        val exception = assertThrows<NoClothesSuggestionException> {
            dataSource.getClothes(30.0, WeatherCondition.Clear)
        }

        assertEquals("No condition clothes found", exception.message)
    }

    @Test
    fun `getClothes should return only condition clothes when no temperature clothes found`() = runTest {
        // Given
        val testClothesDto = ClothesDto(
            temperature = mapOf("COLD" to InnerClothesDto(listOf("Coat", "Gloves"))),
            condition = mapOf("SNOWY" to InnerClothesDto(listOf("Snow Boots", "Scarf")))
        )

        coEvery { mockFindFlow.collect(any()) } coAnswers {
            val collector = arg<FlowCollector<ClothesDto>>(0)
            collector.emit(testClothesDto)
        }

        coEvery { mockCollection.find() } returns mockFindFlow

        coEvery { mockMapper.mapTemperature(30.0) } returns "HOT"
        coEvery { mockMapper.mapCondition(WeatherCondition.Snowy) } returns "SNOWY"

        // When & Then
        val exception = assertThrows<NoClothesSuggestionException> {
            dataSource.getClothes(30.0, WeatherCondition.Snowy)
        }

        assertEquals("No temperature clothes found", exception.message)
    }

    @Test
    fun `getClothes should throw exception when no clothes data found in DB`() = runTest {
        // Given
        coEvery { mockFindFlow.collect(any()) } coAnswers {
        }

        coEvery { mockCollection.find() } returns mockFindFlow
        // When & Then
        val exception = assertThrows<NoClothesSuggestionException> {
            dataSource.getClothes(20.0, WeatherCondition.Clear)
        }

        assertEquals("No clothes suggestion found", exception.message)
    }

    @Test
    fun `getClothes should return clothes for extreme temperatures`() = runTest {
        // Given
        val testClothesDto = ClothesDto(
            temperature = mapOf("EXTREME_COLD" to InnerClothesDto(listOf("Thermal Underwear", "Winter Coat", "Insulated Boots"))),
            condition = mapOf("CLEAR" to InnerClothesDto(listOf("Sunglasses")))
        )

        coEvery { mockFindFlow.collect(any()) } coAnswers {
            val collector = arg<FlowCollector<ClothesDto>>(0)
            collector.emit(testClothesDto)
        }

        coEvery { mockCollection.find() } returns mockFindFlow

        coEvery { mockMapper.mapTemperature(-30.0) } returns "EXTREME_COLD"
        coEvery { mockMapper.mapCondition(WeatherCondition.Clear) } returns "CLEAR"

        // When
        val result = dataSource.getClothes(-30.0, WeatherCondition.Clear)

        // Then
        assertEquals(4, result.size)
        assertTrue(result.contains("Thermal Underwear"))
        assertTrue(result.contains("Winter Coat"))
        assertTrue(result.contains("Insulated Boots"))
        assertTrue(result.contains("Sunglasses"))
    }

    @Test
    fun `getClothes should handle multiple clothes data in DB and use the first one`() = runTest {
        // Given
        val firstClothesDto = ClothesDto(
            temperature = mapOf("MILD" to InnerClothesDto(listOf("Light Jacket", "Jeans"))),
            condition = mapOf("CLOUDY" to InnerClothesDto(listOf("Hat")))
        )

        val secondClothesDto = ClothesDto(
            temperature = mapOf("MILD" to InnerClothesDto(listOf("Sweater", "Pants"))),
            condition = mapOf("CLOUDY" to InnerClothesDto(listOf("Scarf")))
        )

        var emitted = false
        coEvery { mockFindFlow.collect(any()) } coAnswers {
            val collector = arg<FlowCollector<ClothesDto>>(0)
            if (!emitted) {
                collector.emit(firstClothesDto)
                emitted = true
            } else {
                collector.emit(secondClothesDto)
            }
        }

        coEvery { mockCollection.find() } returns mockFindFlow

        coEvery { mockMapper.mapTemperature(15.0) } returns "MILD"
        coEvery { mockMapper.mapCondition(WeatherCondition.Cloudy) } returns "CLOUDY"

        // When
        val result = dataSource.getClothes(15.0, WeatherCondition.Cloudy)
        // Then
        assertEquals(3, result.size)
        assertTrue(result.contains("Light Jacket"))
        assertTrue(result.contains("Jeans"))
        assertTrue(result.contains("Hat"))
    }
}