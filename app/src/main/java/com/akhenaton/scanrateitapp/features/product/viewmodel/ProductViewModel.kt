package com.akhenaton.scanrateitapp.features.product.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.FirestoreRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: FirestoreRepository) : ViewModel() {

    val currentUser: FirebaseUser? get() = repository.user

    private val _state = MutableLiveData<ProductViewState>()
    val state get() = _state

    fun getProductReviews(ean: String) = viewModelScope.launch {
        _state.value = ProductViewState.Loading
        val result = repository.getProductReviews(ean)
        handleResult(result)
    }

    private fun handleResult(result: Resource<List<ReviewModel>>) {
        when(result) {
            is Resource.Failure -> _state.value = ProductViewState.Error
            is Resource.Success -> _state.value = ProductViewState.Success(result.result)
        }
    }
}
