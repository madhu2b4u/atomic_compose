package com.demo.sport.main.domain

import com.demo.sport.main.data.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainUseCaseImpl @Inject constructor(private val repository: MainRepository) :
    MainUseCase {
    override suspend fun getSports() = repository.getSports()
}
