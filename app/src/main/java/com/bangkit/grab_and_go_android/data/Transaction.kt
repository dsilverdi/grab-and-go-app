package com.bangkit.grab_and_go_android.data

data class Transaction(
    val id: Long,
    val paymentType: String,
    val total: Double?,
    val midtransUrl: String?
)
