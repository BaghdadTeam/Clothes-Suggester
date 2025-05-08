package org.baghdad

import di.appModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin


fun main() {
    runBlocking {
        startKoin {
            modules(appModule)
        }
        stopKoin()
    }
}