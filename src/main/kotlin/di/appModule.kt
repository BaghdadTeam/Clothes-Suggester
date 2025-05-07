package di
import org.baghdad.data.repository.clothes.ClothesRepositoryImpl
import org.baghdad.data.source.remote.clothes.MongoClientProvider
import org.baghdad.data.source.remote.clothes.MongoClothesDataSource
import org.baghdad.data.source.remote.clothes.MongoClothesMapper
import org.koin.dsl.module

val appModule = module {
    single { MongoClientProvider() }
    single { MongoClothesMapper() }
    single { MongoClothesDataSource(get(),get())}
    single { ClothesRepositoryImpl(get()) }
}