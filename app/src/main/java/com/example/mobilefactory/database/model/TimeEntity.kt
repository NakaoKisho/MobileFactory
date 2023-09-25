package com.example.mobilefactory.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "time",
)
data class TimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime: String,
    val endingTime: String,
    val registeredTime: String,
    val result: Boolean
)
