package com.vass.coursevass.storage

interface Storage {
    fun saveToken(token: String)
    fun getToken():String
    fun clearToken()
    fun authUsers():Boolean
}