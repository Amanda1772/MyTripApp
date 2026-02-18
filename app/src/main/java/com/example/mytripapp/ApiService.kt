package com.example.mytripapp

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("trips")
    fun getTrips(): Call<List<TripItem>>

    @DELETE("trips/{id}")
    fun deleteTrip(@Path("id") id: Int): Call<Void>
}
