package com.akhenaton.scanrateitapp.features.home.interactor

import com.akhenaton.scanrateitapp.features.product.mapper.ProductMapper
import com.akhenaton.scanrateitapp.features.product.repository.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeInteractorImpl(
    private val api: ProductApi,
    private val mapper: ProductMapper
) : HomeInteractor {
    override suspend fun fetchProduct(code: String): FetchProductState =
        withContext(Dispatchers.Default) {
            try {
                val response = api.getProduct(code)
                when {
                    response?.body() != null -> {
                        val productModel =
                            mapper.mapProductResponseToProductModel(response.body()!!)
                        FetchProductState.DisplayProduct(productModel)
                    }
                    else -> {
                        FetchProductState.DisplayEmpty
                    }
                }
            } catch (ex: Exception) {
                FetchProductState.DisplayError(ex.message.toString())
            }
        }
}
