package com.vass.coursevass.ui.taksform

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vass.coursevass.network.service.TaskService
import com.vass.coursevass.network.service.db.SaveTaskDto
import com.vass.coursevass.utils.storage.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaksViewModel @Inject constructor(
    private val listTask: TaskService,
    private var storage: Storage
): ViewModel() {
    var saveTaskDto = MutableLiveData<SaveTaskDto>()
    var auth = MutableLiveData<Boolean>()
    fun saveTask(name: String, description:String, status:String , assignedTo:String, dueDate:Date){
        viewModelScope.launch(Dispatchers.IO) {
            val lists = listTask.saveTask(SaveTaskDto(name,description,status,assignedTo,dueDate))
            if (lists.isSuccessful) {
                Log.d("verificacion", "esto ${lists.body()}")
                saveTaskDto.postValue(lists.body())
            }
            var statusfinal: Int = lists.code()
            when (statusfinal) {
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