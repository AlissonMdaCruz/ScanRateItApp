package com.akhenaton.scanrateitapp.features.recoveraccess.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.AuthRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import kotlinx.coroutines.launch

class RecoverAccessViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableLiveData<RecoverAccessViewState>()
    val state get() = _state

    fun recoverAccess(email: String) = viewModelScope.launch {
        _state.value = RecoverAccessViewState.Loading
        if (email.isEmpty()) {
            _state.value = RecoverAccessViewState.Error(EMPTY_EMAIL)
        } else {
            val result = repository.recoverAccess(email)
            handleResult(result)
        }
    }

    private fun handleResult(result: Resource<Boolean>) {
        when (result) {
            is Resource.Success -> _state.value = RecoverAccessViewState.Success
            is Resource.Failure -> _state.value =
                RecoverAccessViewState.Error(result.exception.message ?: "")
        }

    }

    companion object {
        private const val EMPTY_EMAIL = "Campo email vazio"
    }
}
