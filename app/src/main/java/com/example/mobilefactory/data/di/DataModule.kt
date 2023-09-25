package com.example.mobilefactory.data.di

import com.example.mobilefactory.data.OfflineTimeRepository
import com.example.mobilefactory.data.TimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsTimeRepository(
        timeRepository: OfflineTimeRepository,
    ): TimeRepository
}