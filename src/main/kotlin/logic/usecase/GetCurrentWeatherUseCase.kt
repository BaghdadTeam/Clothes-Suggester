import org.baghdad.logic.module.entities.CurrentWeather
import org.baghdad.logic.module.exceptions.NotValidCityNameException
import org.baghdad.logic.repository.WeatherRepository

class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String): CurrentWeather {

        validateCityName(cityName)

        return weatherRepository.getCurrentWeatherByCityName(cityName)
    }
    private fun validateCityName(cityName: String) {
        if (cityName.trim().isEmpty()) {
            throw NotValidCityNameException("City name can not be empty")
        }
        if (!cityName.matches("^[a-zA-Z\\s]*$".toRegex())) {
            throw NotValidCityNameException("City name can only contain letters and spaces")
        }
    }

}
