package com.demo.sport.main.data.source

import com.demo.sport.main.data.models.Sport
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor() : LocalDataSource {

    override suspend fun getSports(): List<Sport> {
        return Sport.createMockedSports()
    }

}