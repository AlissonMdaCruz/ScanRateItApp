package com.akhenaton.scanrateitapp.features.home.viewmodel

import com.akhenaton.scanrateitapp.features.product.model.ProductModel

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Success(val product: ProductModel) : HomeViewState()
    data class Error(val message: String) : HomeViewState()
}
