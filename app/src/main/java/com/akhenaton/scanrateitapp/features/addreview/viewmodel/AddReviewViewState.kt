package com.akhenaton.scanrateitapp.features.addreview.viewmodel

sealed class AddReviewViewState {
    object Loading : AddReviewViewState()
    object Success : AddReviewViewState()
    data class Error(val message: String) : AddReviewViewState()
}
