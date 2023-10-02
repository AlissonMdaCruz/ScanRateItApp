package com.akhenaton.scanrateitapp.features.ratings.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.FirestoreRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import kotlinx.coroutines.launch

class RatingsViewModel(private val repository: FirestoreRepository) : ViewModel() {

    private val _state = MutableLiveData<RatingsViewState>()
    val state get() = _state

    fun getUserReviews() = viewModelScope.launch {
        _state.value = RatingsViewState.Loading
        val result = repository.getUserReviews()
        handleResult(result)
    }

    private fun handleResult(result: Resource<List<ReviewModel>>) {
        when(result) {
            is Resource.Failure -> onError()
            is Resource.Success -> onSuccess(result.result)
        }
    }

    private fun onSuccess(list: List<ReviewModel>) {
        when {
            list.isEmpty() -> _state.value = RatingsViewState.Empty
            else -> _state.value = RatingsViewState.Success(list)
        }
    }

    private fun onError() {
        _state.value = RatingsViewState.Error
    }
}