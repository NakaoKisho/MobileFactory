package com.example.mobilefactory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mobilefactory.database.model.TimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {
    @Insert
    fun insert(time: TimeEntity)

    @Query("SELECT * FROM time")
    fun getAll(): Flow<List<TimeEntity>>
}