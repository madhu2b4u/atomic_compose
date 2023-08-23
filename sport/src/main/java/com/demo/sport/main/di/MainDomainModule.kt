package com.demo.sport.main.di

import com.demo.sport.main.data.repository.MainRepository
import com.demo.sport.main.data.repository.MainRepositoryImpl
import com.demo.sport.main.domain.MainUseCase
import com.demo.sport.main.domain.MainUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MainDomainModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: MainRepositoryImpl
    ): MainRepository

    @Binds
    abstract fun bindsUseCase(
        useCaseImpl: MainUseCaseImpl
    ): MainUseCase
}