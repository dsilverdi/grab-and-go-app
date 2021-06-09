package com.bangkit.grab_and_go_android.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionAction(
    @SerializedName("method")
    val method: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)