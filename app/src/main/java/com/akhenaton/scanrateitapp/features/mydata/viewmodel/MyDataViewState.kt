package com.akhenaton.scanrateitapp.features.mydata.viewmodel

import com.google.firebase.auth.FirebaseUser

sealed class MyDataViewState {
    data class ShowUser(val user: FirebaseUser) : MyDataViewState()
    data class Error(val message: String) : MyDataViewState()
}
