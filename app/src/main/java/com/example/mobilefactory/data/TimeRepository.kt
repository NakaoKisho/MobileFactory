package com.example.mobilefactory.data

import com.example.mobilefactory.database.model.TimeEntity
import kotlinx.coroutines.flow.Flow

interface TimeRepository {
    fun getAllTime(): Flow<List<TimeEntity>>
    suspend fun insertTime(time: TimeEntity)
}