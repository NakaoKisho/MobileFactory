package com.example.mobilefactory.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val mbfDispatcher: MbfDispatchers)

enum class MbfDispatchers {
    IO,
}