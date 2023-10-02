package com.akhenaton.scanrateitapp.features.mydata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MyDataViewModel : ViewModel() {

    private val _state = MutableLiveData<MyDataViewState>()
    val state: LiveData<MyDataViewState> = _state

    fun fetchData() {
        val user = FirebaseAuth.getInstance().currentUser
        when {
            user != null -> _state.value = MyDataViewState.ShowUser(user)
            else -> _state.value = MyDataViewState.Error(ERROR_MESSAGE)
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "Ocorreu um erro, tente mais tarde."
    }
}
