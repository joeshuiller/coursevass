package com.vass.coursevass.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vass.coursevass.network.service.TaskService
import com.vass.coursevass.network.service.db.TaskListDto
import com.vass.coursevass.utils.storage.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listTask: TaskService,
    private var storage: Storage
) : ViewModel() {
    var taskRegister = MutableLiveData<List<TaskListDto>>()
    var auth = MutableLiveData<Boolean>()
    fun getTaskList(){
        viewModelScope.launch(Dispatchers.IO) {
            val listaks = listTask.getTaskList()
            if (listaks.isSuccessful) {
                Log.d("verificacion", "esto ${listaks.body()}")
                taskRegister.postValue(listaks.body())
                auth.postValue(listaks.isSuccessful)
            }
            var status: Int = listaks.code()
            when (status) {
                400 -> {
                    auth.postValue(false)
                    storage.clearToken()
                }
                500 -> {
                    auth.postValue(false)
                    storage.clearToken()
                }
            }
        }
    }
}