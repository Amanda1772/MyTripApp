package com.example.mytripapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {

    @Query("SELECT * FROM trips ORDER BY id DESC")
    fun getAllTrips(): LiveData<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE pinned = 1")
    fun getPinnedTrips(): LiveData<List<TripEntity>>

    @Insert
    suspend fun insert(trip: TripEntity)

    @Update
    suspend fun update(trip: TripEntity)

    @Delete
    suspend fun delete(trip: TripEntity)
}