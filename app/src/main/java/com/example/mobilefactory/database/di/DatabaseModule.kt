package com.example.mobilefactory.database.di

import android.content.Context
import androidx.room.Room
import com.example.mobilefactory.database.MbfDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesMbfDatabase(
        @ApplicationContext context: Context,
    ): MbfDatabase = Room.databaseBuilder(
        context,
        MbfDatabase::class.java,
        "nia-database",
    ).build()
}