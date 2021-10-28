package com.vass.coursevass.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vass.coursevass.network.service.AuthServices
import com.vass.coursevass.network.service.UserService
import com.vass.coursevass.network.service.db.LoginDto
import com.vass.coursevass.network.service.db.RegistrationDto
import com.vass.coursevass.network.service.db.UserDto
import com.vass.coursevass.utils.storage.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class serviceViewModel @Inject constructor(
    private var authService: AuthServices,
    private val storage: Storage,
    private val userService: UserService
) : ViewModel() {
    var auth = MutableLiveData<Boolean>()
    var userRegister = MutableLiveData<List<UserDto>>()
    var userCreate = MutableLiveData<UserDto>()
    fun authLogin(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = authService.login(LoginDto(email, password))
            if (loginResponse.isSuccessful){
                storage.saveToken(loginResponse.body()!!.accessToken)
            }
            auth.postValue(loginResponse.isSuccessful)
        }
    }
    fun createUsers(name: String,  email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = userService.saveUser(
                RegistrationDto(
                    name,
                    email,
                    password
                )
            )
            userCreate.postValue(loginResponse.body())
        }
    }
    fun listUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = userService.getUsersList()
            userRegister.postValue(loginResponse.body())
        }
    }
}