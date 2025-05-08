package org.baghdad.data.source.remote.clothes

import kotlinx.coroutines.flow.firstOrNull
import org.baghdad.data.clothes.ClothesDataSource
import org.baghdad.data.dto.ClothesDto
import org.baghdad.logic.module.entities.WeatherCondition
import org.baghdad.logic.module.exceptions.NoClothesSuggestionException

class MongoClothesDataSource(
    private val client : MongoClientProvider,
    private val mapper: MongoClothesMapper,
    private val databaseName: String = "clothes-db",
    private val collectionName: String = "clothes"
) : ClothesDataSource {

    override suspend fun getClothes(temperature: Double, weatherCondition: WeatherCondition): List<String> {
        client.getClient().use { client ->
            val database = client.getDatabase(databaseName)
            val collection = database.getCollection<ClothesDto>(collectionName)

            val dto = collection.find().firstOrNull()
                ?: throw NoClothesSuggestionException("No clothes suggestion found")

            val temperatureClothes =
                dto.temperature[mapper.mapTemperature(temperature)]?.clothes
                    ?: throw NoClothesSuggestionException("No temperature clothes found")
            val conditionClothes =
                dto.condition[mapper.mapCondition(weatherCondition)]?.clothes
                    ?: throw NoClothesSuggestionException("No condition clothes found")
            return temperatureClothes + conditionClothes
        }
    }
}