package com.akhenaton.scanrateitapp.features.login.viewmodel

sealed class LoginViewState {
    object Loading : LoginViewState()
    object Success : LoginViewState()
    data class Error(val message: String) : LoginViewState()
}
