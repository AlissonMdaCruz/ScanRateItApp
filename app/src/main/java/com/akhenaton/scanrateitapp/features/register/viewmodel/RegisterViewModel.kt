package com.akhenaton.scanrateitapp.features.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.AuthRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    val currentUser: FirebaseUser? get() = repository.currentUser

    private val _state = MutableLiveData<RegisterViewState>()
    val state get() = _state

    fun validateSignUp(name: String, email: String, password: String, confirmPassword: String) {
        _state.value = RegisterViewState.Loading
        when {
            name.isEmpty() -> _state.value = RegisterViewState.Error(EMPTY_NAME)
            email.isEmpty() -> _state.value = RegisterViewState.Error(EMPTY_EMAIL)
            password.isEmpty() -> _state.value = RegisterViewState.Error(EMPTY_PASSWORD)
            confirmPassword.isEmpty() -> _state.value = RegisterViewState.Error(
                EMPTY_CONFRIM_PASSWORD
            )
            password != confirmPassword -> _state.value = RegisterViewState.Error(DIFF_PASSWORD)
            else -> signUp(name, email, password)
        }
    }

    private fun signUp(name: String, email: String, password: String) = viewModelScope.launch {
        val result = repository.signup(name, email, password)
        handleResult(result)
    }

    private fun handleResult(result: Resource<FirebaseUser>) {
        when (result) {
            is Resource.Success -> _state.value = RegisterViewState.Success
            is Resource.Failure -> _state.value =
                RegisterViewState.Error(result.exception.message ?: "")
        }
    }

    companion object {
        private const val EMPTY_NAME = "Campo nome vazio"
        private const val EMPTY_EMAIL = "Campo email vazio"
        private const val EMPTY_PASSWORD = "Campo senha vazio"
        private const val EMPTY_CONFRIM_PASSWORD = "Campo confirmação de senha vazio"
        private const val DIFF_PASSWORD = "As senhas estão diferentes"
    }
}
