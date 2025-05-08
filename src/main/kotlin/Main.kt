package org.baghdad

import di.appModule
import kotlinx.coroutines.runBlocking
import org.baghdad.presentation.app.WeatherApp
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin


fun main() {
    startKoin {
        modules(appModule)
    }
runBlocking{
    val app = getKoin().get<WeatherApp>()
    app.run()
}
}