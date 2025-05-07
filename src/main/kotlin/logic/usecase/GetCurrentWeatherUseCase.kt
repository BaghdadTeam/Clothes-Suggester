package logic.usecase
import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.repository.WeatherRepository

class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String): CurrentWeather {
        return weatherRepository.getCurrentWeatherByCityName(cityName)
    }
}
