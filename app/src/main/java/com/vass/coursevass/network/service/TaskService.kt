package com.vass.coursevass.network.service

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface TaskService {
    /**
     * @author Janes Saenz
     * 19/10/21.
     */
    @DELETE("api/task/{id}")
    suspend fun deleteTask(@Path("id") taskId: String): Response<Void>
}