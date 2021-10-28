package com.vass.coursevass.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vass.coursevass.network.service.SaveTaskService
import com.vass.coursevass.network.service.db.TaskListDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listTask: SaveTaskService
) : ViewModel() {
    var taskRegister = MutableLiveData<List<TaskListDto>>()
    fun getTaskList(){
        viewModelScope.launch(Dispatchers.IO) {
            val listaks = listTask.getTaskList()
            if (listaks.isSuccessful) {
                Log.d("verificacion", "esto ${listaks.body()}")
                taskRegister.postValue(listaks.body())
            }
        }
    }
}