package com.demo.sport.main.domain

import androidx.lifecycle.LiveData
import com.demo.sport.common.Result
import com.demo.sport.main.data.models.Sport

interface MainUseCase {

    suspend fun getSports(): LiveData<Result<List<Sport>>>

}