package logic.usecase
import org.baghdad.logic.module.entities.ClothesSuggestion
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.repository.ClothesRepository

class GetClothesSuggestionUseCase(
    private val clothesRepository: ClothesRepository
) {
    suspend operator fun invoke(weather: CurrentWeather): ClothesSuggestion {
        return clothesRepository.getClothesByWeatherStatus(weather)
    }
}
