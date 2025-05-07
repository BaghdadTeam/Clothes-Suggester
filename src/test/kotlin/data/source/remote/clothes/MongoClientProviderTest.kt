package data.source.remote.clothes
import com.google.common.truth.Truth.assertThat
import org.baghdad.data.source.remote.clothes.MongoClientProvider
import org.baghdad.logic.module.exceptions.MongoDBConnectionException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MongoClientProviderTest {

    @Test
    fun `should create MongoClient when connection string is available`() {
        // Given
        val connectionString = "mongodb://localhost:27017"
        val provider = MongoClientProvider(connectionString)
        // When
        val client = provider.getClient()
        // Then
        assertThat(client).isNotNull()
    }
    @Test
    fun `should throw exception when connection string is not available`() {
        // Given
        val connectionString = null
        // When & Then
        assertThrows<MongoDBConnectionException> {MongoClientProvider(connectionString)}
    }
}