package data.repository.clothes

import com.google.common.truth.Truth.assertThat
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import org.baghdad.data.clothes.ClothesDataSource
import org.baghdad.data.dto.ClotheDto
import org.baghdad.data.repository.clothes.ClothesRepositoryImpl
import org.junit.jupiter.api.*
import java.util.UUID

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
        coEvery { dataSource.getClothes() } returns listOf(
            ClotheDto(
                UUID.randomUUID().toString(),
                name = "Sweater",
                description = "A nice sweater",
                clothType = "HeatResistant",
                price = 10.0,
                imageUrl = "https://example.com/sweater.jpg",

                )
        )
        // When
        val result = runBlocking {
            repository.getClothes()
        }
        // Then
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `Should return ClothesSuggestion get clothes by weather status when no clothes found`() {
        // Given
        coEvery { dataSource.getClothes() } returns emptyList()
        // When
        val result = runBlocking {
            repository.getClothes()
        }
        // Then
        assertThat(result).isEmpty()
    }
}