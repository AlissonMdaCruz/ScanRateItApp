package com.akhenaton.scanrateitapp.features.changepass.viewmodel

sealed class ChangePassViewState {
    object Loading : ChangePassViewState()
    object Success : ChangePassViewState()
    data class Error(val message: String) : ChangePassViewState()
}
