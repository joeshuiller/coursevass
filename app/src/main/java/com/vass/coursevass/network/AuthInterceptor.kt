package com.vass.coursevass.network

import com.vass.coursevass.storage.Storage
import com.vass.coursevass.utils.AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private  val  storege: Storage): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = storege.getToken()
        if (token.isNotEmpty()){
            request.addHeader(AUTHORIZATION, "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}