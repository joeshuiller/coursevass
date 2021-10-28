package com.vass.coursevass.utils.storage

import android.content.Context
import com.vass.coursevass.utils.SHARED_PREFERENCES_FILES_NAME
import com.vass.coursevass.utils.TOKEN_KEY

class LocalStorage(context: Context): Storage {
    private  val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILES_NAME, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPref.edit().putString(TOKEN_KEY,token).apply()
    }
    override fun getToken(): String {
        return sharedPref.getString(TOKEN_KEY,"")!!
    }
    override fun clearToken() {
        return sharedPref.edit().remove(TOKEN_KEY).apply()
    }

    override fun authUsers(): Boolean {
        val token = getToken()
        return token != ""
    }
}