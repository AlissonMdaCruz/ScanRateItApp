package com.akhenaton.scanrateitapp.features.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.common.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = AuthRepositoryImpl(FirebaseAuth.getInstance())

        return LoginViewModel(repository) as T
    }
}
