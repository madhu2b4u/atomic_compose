package com.demo.sport.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

interface AppCoroutineDispatchers {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}

@Singleton
object AppCoroutineDispatcherProvider {

    private val dispatcher = object : AppCoroutineDispatchers {
        override fun io() = Dispatchers.IO
        override fun main() = Dispatchers.Main
        override fun default() = Dispatchers.Default
        override fun unconfined() = Dispatchers.Unconfined
    }

    fun dispatcher() = dispatcher
}