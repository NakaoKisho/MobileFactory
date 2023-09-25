package com.example.mobilefactory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilefactory.database.dao.TimeDao
import com.example.mobilefactory.database.model.TimeEntity

@Database(entities = [TimeEntity::class], version = 1, exportSchema = false)
abstract class MbfDatabase: RoomDatabase() {
    abstract fun timeDao(): TimeDao
}