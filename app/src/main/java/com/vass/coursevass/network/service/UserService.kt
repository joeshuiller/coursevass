package com.vass.coursevass.network.service

import com.vass.coursevass.network.service.db.RegistrationDto
import com.vass.coursevass.network.service.db.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    /**
     * @author Janes Saenz
     * 19/10/21.
     */
    @GET("api/user/all")
    suspend fun getUsersList(): Response<List<UserDto>>

    @POST("api/user")
    suspend fun saveUser(@Body registrationDto: RegistrationDto): Response<UserDto>
}