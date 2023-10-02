package com.akhenaton.scanrateitapp.features.productratings.viewmodel

import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel

sealed class ProductRatingsViewState {
    object Loading : ProductRatingsViewState()
    object Empty : ProductRatingsViewState()
    data class Success(val list: List<ReviewModel>) : ProductRatingsViewState()
    object Error : ProductRatingsViewState()
}
