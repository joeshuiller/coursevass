package com.vass.coursevass.utils.storage

interface Storage {
    fun saveToken(token: String)
    fun getToken():String
    fun clearToken()
    fun authUsers():Boolean
}