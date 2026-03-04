package com.example.mytripapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val destination: String,

    val startDate: String,

    val endDate: String,

    val notes: String,

    val pinned: Boolean = false
)