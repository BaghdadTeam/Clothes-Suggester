package org.baghdad

import di.appModule
import kotlinx.coroutines.runBlocking
import org.baghdad.data.source.remote.clothes.MongoClothesDataSource
import org.baghdad.logic.module.entities.WeatherCondition
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    runBlocking {
        startKoin {
            modules(appModule)
        }
        stopKoin()
    }
}