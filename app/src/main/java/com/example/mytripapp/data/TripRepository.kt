package com.example.mytripapp.data

import androidx.lifecycle.LiveData

class TripRepository(private val dao: TripDao) {

    val allTrips: LiveData<List<TripEntity>> = dao.getAllTrips()
    val pinnedTrips: LiveData<List<TripEntity>> = dao.getPinnedTrips()

    suspend fun insert(trip: TripEntity) = dao.insert(trip)
    suspend fun update(trip: TripEntity) = dao.update(trip)
    suspend fun delete(trip: TripEntity) = dao.delete(trip)
}