package com.akhenaton.scanrateitapp.common.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewModel(
    val ean: String,
    val product: String,
    val userId: String,
    val userName: String,
    val review: String,
    val rating: Float
) : Parcelable
