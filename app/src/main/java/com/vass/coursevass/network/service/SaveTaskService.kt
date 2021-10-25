package com.vass.coursevass.network.service

import com.vass.coursevass.network.service.db.SaveTaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SaveTaskService {

    @POST("auth")
    suspend fun SaveTask(@Body saveTaskDto: SaveTaskDto): Response<SaveTaskDto>
}