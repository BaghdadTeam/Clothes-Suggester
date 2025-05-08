package logic.usecase
import org.baghdad.logic.comman.WeatherToClotheTypeMapper
import org.baghdad.logic.module.entities.Clothe
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.repository.ClothesRepository

class GetClothesSuggestionUseCase(
    private val clothesRepository: ClothesRepository,
) {
    suspend operator fun invoke(weather: CurrentWeather): List<Clothe> {
        val clothes = clothesRepository.getClothes()
        val clothesTypes = WeatherToClotheTypeMapper.mapToClotheTypes(weather.temperatureInCelsius, weather.weatherCondition)
        return clothes.filter { it.clothType in clothesTypes }
    }
}
