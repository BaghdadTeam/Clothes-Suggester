package di
import org.koin.dsl.module

val appModule = module {
    single { MongoClientProvider() }
    single { MongoClothesMapper() }
    single { MongoClothesDataSource(get(),get())}
    single { ClothesRepositoryImpl(get()) }
val appModule = module{

    // Ktor HTTP Client with JSON serialization
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // Remote data source
    single<WeatherDataSource> { ApiWeatherDataSource(get()) }
    // Repositories
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

}