package com.akhenaton.scanrateitapp.features.changepass.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.AuthRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import kotlinx.coroutines.launch

class ChangePassViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableLiveData<ChangePassViewState>()
    val state get() = _state

    fun validateChangePass(oldPass: String, newPass: String, confirmPass: String) {
        _state.value = ChangePassViewState.Loading
        when {
            oldPass.isEmpty() -> _state.value = ChangePassViewState.Error(EMPTY_OLD_PASS)
            newPass.isEmpty() -> _state.value = ChangePassViewState.Error(EMPTY_NEW_PASS)
            confirmPass.isEmpty() -> _state.value = ChangePassViewState.Error(
                EMPTY_CONFRIM_PASSWORD
            )
            newPass != confirmPass -> _state.value = ChangePassViewState.Error(DIFF_PASSWORD)
            else -> changePass(oldPass, newPass)
        }
    }

    private fun changePass(oldPass: String, newPass: String) = viewModelScope.launch {
        val result = repository.changeData(oldPass, newPass)
        handleResult(result)
    }

    private fun handleResult(result: Resource<Boolean>) {
        when (result) {
            is Resource.Success -> _state.value = ChangePassViewState.Success
            is Resource.Failure -> _state.value =
                ChangePassViewState.Error(result.exception.message ?: "")
        }
    }

    companion object {
        private const val EMPTY_OLD_PASS = "Campo senha vazio"
        private const val EMPTY_NEW_PASS = "Campo nova senha vazio"
        private const val EMPTY_CONFRIM_PASSWORD = "Campo confirmação de senha vazio"
        private const val DIFF_PASSWORD = "As senhas estão diferentes"
    }
}
