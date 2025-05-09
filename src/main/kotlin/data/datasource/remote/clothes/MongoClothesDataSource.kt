package data.datasource.remote.clothes

import kotlinx.coroutines.flow.toList
import data.datasource.ClothesDataSource
import org.baghdad.data.dto.ClotheDto
import org.baghdad.data.dto.MongoClothesDocument
import org.baghdad.logic.module.exceptions.NoClothesSuggestionException

class MongoClothesDataSource(
    private val client: MongoClientProvider,
    private val databaseName: String = "clothes-db",
    private val collectionName: String = "clothes"
) : ClothesDataSource {

    override suspend fun getClothes(): List<ClotheDto> {
        try {
            client.getClient().use { mongoClient ->
                val database = mongoClient.getDatabase(databaseName)
                val collection = database.getCollection<MongoClothesDocument>(collectionName)
                val allDocuments = collection.find().toList()
                val allClothes = allDocuments.flatMap { it.clothes }
                return allClothes
            }
        } catch (e: NoClothesSuggestionException) {
            throw e
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch clothes from MongoDB: ${e.message}", e) }
    }

}