package com.demo.sport.main.data.source

import com.demo.sport.main.data.models.Sport

interface LocalDataSource {
    suspend fun getSports(): List<Sport>
}