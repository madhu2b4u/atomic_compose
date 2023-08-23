package com.demo.sport.main.data.repository

import androidx.lifecycle.LiveData
import com.demo.sport.common.Result
import com.demo.sport.main.data.models.Sport

interface MainRepository {
    suspend fun getSports(): LiveData<Result<List<Sport>>>
}