package com.akhenaton.scanrateitapp.features.home.interactor

import com.akhenaton.scanrateitapp.features.product.model.ProductModel

sealed class FetchProductState {
    object DisplayEmpty : FetchProductState()
    data class DisplayProduct(val product: ProductModel) : FetchProductState()
    data class DisplayError(val message: String) : FetchProductState()
}
