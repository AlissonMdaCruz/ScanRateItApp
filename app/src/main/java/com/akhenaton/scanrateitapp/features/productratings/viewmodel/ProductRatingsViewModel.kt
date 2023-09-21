package com.akhenaton.scanrateitapp.features.productratings.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.FirestoreRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import kotlinx.coroutines.launch

class ProductRatingsViewModel(private val repository: FirestoreRepository) : ViewModel() {

    private val _state = MutableLiveData<ProductRatingsViewState>()
    val state get() = _state

    fun getProductReviews(ean: String) = viewModelScope.launch {
        _state.value = ProductRatingsViewState.Loading
        val result = repository.getProductReviews(ean)
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
            list.isEmpty() -> _state.value = ProductRatingsViewState.Empty
            else -> _state.value = ProductRatingsViewState.Success(list)
        }
    }

    private fun onError() {
        _state.value = ProductRatingsViewState.Error
    }
}
