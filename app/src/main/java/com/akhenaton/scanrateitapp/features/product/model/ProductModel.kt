package com.akhenaton.scanrateitapp.features.product.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    var code : String,
    var nome : String
) : Parcelable
