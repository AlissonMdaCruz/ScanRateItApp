package com.akhenaton.scanrateitapp.features.review.viewmodel

sealed class ReviewViewState {
    object Loading : ReviewViewState()
    object Success : ReviewViewState()
    data class Error(val message: String) : ReviewViewState()
}
