package com.vass.coursevass.network.service

import com.vass.coursevass.network.service.db.SaveTaskDto
import com.vass.coursevass.network.service.db.TaskListDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SaveTaskService {

    @POST("api/task")
    suspend fun SaveTask(@Body saveTaskDto: SaveTaskDto): Response<SaveTaskDto>

    @GET("api/task/all")
    suspend fun getTaskList(): Response<List<TaskListDto>>
}