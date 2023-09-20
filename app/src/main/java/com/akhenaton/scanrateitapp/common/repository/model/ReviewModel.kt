package com.akhenaton.scanrateitapp.common.repository.model

data class ReviewModel(
    val ean: String,
    val product: String,
    val userId: String,
    val review: String,
    val rating: Float
)
