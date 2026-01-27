package com.example.myapp.data.remote.service

import com.example.myapp.data.remote.dto.TourDto
import retrofit2.Response
import retrofit2.http.GET

interface TourApi {
    @GET(TOURS)
    suspend fun getTours(): Response<List<TourDto>>


    companion object {
        const val TOURS = "tours"
    }
}
