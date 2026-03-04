package com.example.mytripapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytripapp.data.AppDatabase
import com.example.mytripapp.data.TripEntity
import com.example.mytripapp.data.TripRepository
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: TripRepository

    val trips: LiveData<List<TripEntity>>
    val pinnedTrips: LiveData<List<TripEntity>>

    init {

        val dao = AppDatabase.getDatabase(application).tripDao()

        repo = TripRepository(dao)

        trips = repo.allTrips
        pinnedTrips = repo.pinnedTrips
    }

    fun insertTrip(trip: TripEntity) {
        viewModelScope.launch {
            repo.insert(trip)
        }
    }

    fun updateTrip(trip: TripEntity) {
        viewModelScope.launch {
            repo.update(trip)
        }
    }

    fun deleteTrip(trip: TripEntity) {
        viewModelScope.launch {
            repo.delete(trip)
        }
    }

    fun togglePinned(trip: TripEntity) {

        val updated = trip.copy(pinned = !trip.pinned)

        viewModelScope.launch {
            repo.update(updated)
        }
    }
}