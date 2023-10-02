package com.akhenaton.scanrateitapp.features.product.repository

import com.akhenaton.scanrateitapp.features.product.repository.response.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductApi {
    @GET("produto/{code}")
    suspend fun getProduct(@Path("code") code: String): Response<ProductResponse?>?
}
