package com.akhenaton.scanrateitapp.features.product.viewmodel

import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel

sealed class ProductViewState {
    object Loading : ProductViewState()
    object Error : ProductViewState()
    data class Success(val list: List<ReviewModel>) : ProductViewState()
}
