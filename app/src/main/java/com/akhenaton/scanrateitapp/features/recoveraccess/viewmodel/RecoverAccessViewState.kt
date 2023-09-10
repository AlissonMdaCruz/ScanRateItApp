package com.akhenaton.scanrateitapp.features.recoveraccess.viewmodel

sealed class RecoverAccessViewState {
    object Loading : RecoverAccessViewState()
    object Success : RecoverAccessViewState()
    data class Error(val message: String) : RecoverAccessViewState()
}
