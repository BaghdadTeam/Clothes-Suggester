package org.baghdad

import di.appModule
import org.baghdad.presentation.app.WeatherApp
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

suspend fun main() {
    startKoin {
        modules(appModule)

    }
    val result = getKoin().get<WeatherApp>()
    result.run()


}