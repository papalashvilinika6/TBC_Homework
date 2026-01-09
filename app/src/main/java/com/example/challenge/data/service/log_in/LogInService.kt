package com.example.challenge.data.service.log_in

import com.example.challenge.data.model.log_in.LogInDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LogInService {
    @POST("2d37b201-2d06-451f-9008-422a20dac2f4")
    suspend fun logIn(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<LogInDto>
}