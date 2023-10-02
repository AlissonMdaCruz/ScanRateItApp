package com.akhenaton.scanrateitapp.features.product.repository.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("ean")
    var code : String,
    @SerializedName("vc_nome")
    var nome : String
)
