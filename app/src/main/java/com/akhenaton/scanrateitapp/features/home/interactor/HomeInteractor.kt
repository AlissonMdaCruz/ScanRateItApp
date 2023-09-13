package com.akhenaton.scanrateitapp.features.home.interactor

interface HomeInteractor {
    suspend fun fetchProduct(code: String) : FetchProductState
}
