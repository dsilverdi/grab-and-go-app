package com.bangkit.grab_and_go_android.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("data")
    val transactionData: TransactionData,
    @SerializedName("message")
    val message: String
)