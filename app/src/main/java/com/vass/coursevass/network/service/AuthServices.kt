package com.vass.coursevass.network.service

import com.vass.coursevass.network.service.db.LoginDto
import com.vass.coursevass.network.service.db.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServices {
 /**
  * @author Janes Saenz
  * 19/10/21.
  */
 @POST("auth")
 suspend fun login(@Body loginDto: LoginDto): Response<TokenDto>
}