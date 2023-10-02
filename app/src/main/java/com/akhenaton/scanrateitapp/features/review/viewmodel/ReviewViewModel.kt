package com.akhenaton.scanrateitapp.features.review.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.FirestoreRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val repository: FirestoreRepository
) : ViewModel() {

    val currentUser: FirebaseUser? get() = repository.user

    private val _mutableState = MutableLiveData<ReviewViewState>()
    val state get() = _mutableState

    fun deleteReview(reviewModel: ReviewModel) = viewModelScope.launch {
        _mutableState.value = ReviewViewState.Loading
        val result = repository.deleteReview(reviewModel)
        handleResult(result)
    }

    private fun handleResult(result: Resource<Boolean>) {
        when (result) {
            is Resource.Success -> _mutableState.value = ReviewViewState.Success
            is Resource.Failure -> _mutableState.value =
                ReviewViewState.Error(result.exception.message ?: EMPTY)
        }
    }

    companion object {
        private const val EMPTY = ""
    }
}
