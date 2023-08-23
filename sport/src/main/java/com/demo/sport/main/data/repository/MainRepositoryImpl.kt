package com.demo.sport.main.data.repository

import androidx.lifecycle.liveData
import com.demo.sport.common.Result
import com.demo.sport.main.data.source.LocalDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) :
    MainRepository {
    override suspend fun getSports() = liveData {
        emit(Result.loading())
        try {
            val result = localDataSource.getSports()
            delay(2500)
            emit(Result.success(result))
        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: "", null))
        }
    }
}




