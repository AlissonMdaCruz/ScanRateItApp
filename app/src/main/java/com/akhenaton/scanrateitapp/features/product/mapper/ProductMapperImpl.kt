package com.akhenaton.scanrateitapp.features.product.mapper

import com.akhenaton.scanrateitapp.features.product.model.ProductModel
import com.akhenaton.scanrateitapp.features.product.repository.response.ProductResponse

class ProductMapperImpl: ProductMapper {
    override fun mapProductResponseToProductModel(response: ProductResponse): ProductModel =
        ProductModel(
            response.code,
            response.nome
        )
}
