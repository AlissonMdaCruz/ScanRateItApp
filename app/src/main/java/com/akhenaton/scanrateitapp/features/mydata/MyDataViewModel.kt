package com.akhenaton.scanrateitapp.features.mydata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyDataViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is My data Fragment"
    }
    val text: LiveData<String> = _text
}