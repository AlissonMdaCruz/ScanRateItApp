package com.akhenaton.scanrateitapp.features.recoveraccess.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.common.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth

class RecoverAccessViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = AuthRepositoryImpl(FirebaseAuth.getInstance())

        return RecoverAccessViewModel(repository) as T
    }
}
