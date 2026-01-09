package com.example.challenge.data.service.connection

import com.example.challenge.data.model.connection.ConnectionDto
import retrofit2.Response
import retrofit2.http.GET

interface ConnectionsService {
    @GET("2d37b201-2d06-451f-9008-422a20dac2f4")
    suspend fun getConnections(
    ): Response<List<ConnectionDto>>
}