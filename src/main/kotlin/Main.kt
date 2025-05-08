package org.baghdad

import org.baghdad.di.appModule
import org.koin.core.context.GlobalContext.startKoin

 fun main() {
    startKoin {
        modules(appModule)
    }
}