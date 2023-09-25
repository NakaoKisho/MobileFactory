package com.example.mobilefactory.data

import com.example.mobilefactory.database.dao.TimeDao
import com.example.mobilefactory.database.model.TimeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineTimeRepository @Inject constructor(
    private val timeDao: TimeDao,
): TimeRepository {
    override fun getAllTime(): Flow<List<TimeEntity>> = timeDao.getAll()

    override suspend fun insertTime(time: TimeEntity) {
        timeDao.insert(time)
    }
}