package com.akhenaton.scanrateitapp.features.recoveraccess.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.AuthRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class RecoverAccessViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    val currentUser: FirebaseUser? get() = repository.currentUser

    private val _state = MutableLiveData<RecoverAccessViewState>()
    val state get() = _state

    fun recoverAccess(email: String) = viewModelScope.launch {
        val result = repository.recoverAccess(email)
        handleResult(result)
    }

    private fun handleResult(result: Resource<Boolean>) {
        when (result) {
            is Resource.Success -> _state.value = RecoverAccessViewState.Success
            is Resource.Failure -> _state.value =
                RecoverAccessViewState.Error(result.exception.message ?: "")
        }

    }
}
