package com.demo.sport.main.di

import com.demo.sport.main.data.source.LocalDataSource
import com.demo.sport.main.data.source.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [MainLocalModule.Binders::class])
@InstallIn(SingletonComponent::class)
class MainLocalModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsLocalDataSource(
            localDataSourceImpl: LocalDataSourceImpl
        ): LocalDataSource
    }
}