package com.vass.coursevass.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    // Get the Intent that started this activity and extract the string

    // Capture the layout's TextView and set the string as its text
    val text: LiveData<String> = _text
}