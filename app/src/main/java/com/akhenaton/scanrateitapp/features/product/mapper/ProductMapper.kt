package com.akhenaton.scanrateitapp.features.product.mapper

import com.akhenaton.scanrateitapp.features.product.model.ProductModel
import com.akhenaton.scanrateitapp.features.product.repository.response.ProductResponse

interface ProductMapper {
    fun mapProductResponseToProductModel(response: ProductResponse) : ProductModel
}
