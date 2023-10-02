package com.akhenaton.scanrateitapp.features.addreview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.common.repository.FirestoreRepository
import com.akhenaton.scanrateitapp.common.repository.Resource
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import kotlinx.coroutines.launch

class AddReviewViewModel(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _mutableState = MutableLiveData<AddReviewViewState>()
    val state get() = _mutableState

    fun validateReview(ean: String, product: String, review: String, rating: Float) {
        when {
            review.isEmpty() -> _mutableState.value = AddReviewViewState.Error(EMPTY_REVIEW)
            else -> {
                val reviewModel = getReviewModel(ean, product, review, rating)
                sendReview(reviewModel)
            }
        }
    }

    fun validateEditReview(reviewModel: ReviewModel) {
        when {
            reviewModel.review.isEmpty() -> _mutableState.value =
                AddReviewViewState.Error(EMPTY_REVIEW)
            else -> sendEditReview(reviewModel)
        }
    }

    private fun getReviewModel(
        ean: String,
        product: String,
        review: String,
        rating: Float
    ): ReviewModel =
        ReviewModel(
            ean = ean,
            product = product,
            userId = repository.user?.uid ?: EMPTY,
            userName = repository.user?.displayName ?: EMPTY,
            review = review,
            rating = rating
        )

    private fun sendReview(reviewModel: ReviewModel) = viewModelScope.launch {
        _mutableState.value = AddReviewViewState.Loading
        val result = repository.saveReview(reviewModel)
        handleResult(result)
    }

    private fun sendEditReview(reviewModel: ReviewModel) = viewModelScope.launch {
        _mutableState.value = AddReviewViewState.Loading
        val result = repository.updateReview(reviewModel)
        handleResult(result)
    }

    private fun handleResult(result: Resource<Boolean>) {
        when (result) {
            is Resource.Success -> _mutableState.value = AddReviewViewState.Success
            is Resource.Failure -> _mutableState.value =
                AddReviewViewState.Error(result.exception.message ?: EMPTY)
        }
    }

    companion object {
        private const val EMPTY = ""
        private const val EMPTY_REVIEW = "Campo avaliação vazio"
    }
}
