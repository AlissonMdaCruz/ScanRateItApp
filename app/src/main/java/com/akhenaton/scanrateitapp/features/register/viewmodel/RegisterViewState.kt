package com.akhenaton.scanrateitapp.features.register.viewmodel

sealed class RegisterViewState {
    object Loading : RegisterViewState()
    object Success : RegisterViewState()
    data class Error(val message: String) : RegisterViewState()
}
