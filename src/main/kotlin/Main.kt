package org.baghdad

import org.koin.core.context.GlobalContext.startKoin
import di.appModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun main() {
    runBlocking {
        startKoin {
            modules(appModule)
        }
        stopKoin()
    }
}