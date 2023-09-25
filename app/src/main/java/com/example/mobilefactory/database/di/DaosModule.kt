package com.example.mobilefactory.database.di

import com.example.mobilefactory.database.MbfDatabase
import com.example.mobilefactory.database.dao.TimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTimeDao(
        database: MbfDatabase,
    ): TimeDao = database.timeDao()
}