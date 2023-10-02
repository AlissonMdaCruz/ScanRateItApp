package com.akhenaton.scanrateitapp.features.ratings.viewmodel

import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel

sealed class RatingsViewState {
    object Loading : RatingsViewState()
    object Empty : RatingsViewState()
    data class Success(val list: List<ReviewModel>) : RatingsViewState()
    object Error : RatingsViewState()
}
