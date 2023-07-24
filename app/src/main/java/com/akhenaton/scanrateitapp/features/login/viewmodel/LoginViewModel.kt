package com.akhenaton.scanrateitapp.features.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.AuthRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    val currentUser: FirebaseUser? get() = repository.currentUser

    private val _state = MutableLiveData<LoginViewState>()
    val state get() = _state

    fun validateAccess(email: String, password: String) {
        _state.value = LoginViewState.Loading
        when {
            email.isEmpty() -> _state.value = LoginViewState.Error(EMPTY_EMAIL)
            password.isEmpty() -> _state.value = LoginViewState.Error(EMPTY_PASSWORD)
            else -> accessAccount(email, password)
        }
    }

    private fun accessAccount(email: String, password: String) = viewModelScope.launch {
        val result = repository.login(email, password)
        handleResult(result)
    }

    private fun handleResult(result: Resource<FirebaseUser>) {
        when (result) {
            is Resource.Success -> _state.value = LoginViewState.Success
            is Resource.Failure -> _state.value =
                LoginViewState.Error(result.exception.message ?: "")
        }
    }

    companion object {
        private const val EMPTY_EMAIL = "Empty email"
        private const val EMPTY_PASSWORD = "Empty password"
    }
}
