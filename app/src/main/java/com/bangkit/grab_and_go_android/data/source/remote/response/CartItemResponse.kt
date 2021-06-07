package com.bangkit.grab_and_go_android.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CartItemResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int
)