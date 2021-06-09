package com.bangkit.grab_and_go_android.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class CheckoutRequest(
    @SerializedName("order_id")
    val id: Long,
    @SerializedName("total")
    val total: Int,
    @SerializedName("callback_url")
    val url: String
)
