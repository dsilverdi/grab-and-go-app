package com.bangkit.grab_and_go_android.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionData(
    @SerializedName("actions")
    val actions: List<TransactionAction>,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("fraud_status")
    val fraudStatus: String,
    @SerializedName("gross_amount")
    val grossAmount: String,
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("payment_type")
    val paymentType: String,
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("transaction_id")
    val transactionId: String,
    @SerializedName("transaction_status")
    val transactionStatus: String,
    @SerializedName("transaction_time")
    val transactionTime: String
)