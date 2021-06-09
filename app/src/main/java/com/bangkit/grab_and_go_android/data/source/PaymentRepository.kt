package com.bangkit.grab_and_go_android.data.source

import android.util.Log
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.Transaction
import com.bangkit.grab_and_go_android.data.source.remote.PaymentRemoteDataSource
import com.bangkit.grab_and_go_android.data.source.remote.response.TransactionResponse
import com.bangkit.grab_and_go_android.data.vo.Resource
import com.bangkit.grab_and_go_android.utils.DataMapper
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val paymentRemoteDataSource: PaymentRemoteDataSource
) {
    suspend fun checkout(cart: Cart): Resource<Transaction> {
        val checkoutRequest = DataMapper.cartToCheckoutRequest(cart)
        val type = "application/json".toMediaTypeOrNull()
        val checkoutDetail = Gson().toJson(checkoutRequest)
        val body: RequestBody = checkoutDetail.toRequestBody(type)
        val response = paymentRemoteDataSource.checkout(body)
        if (response is Resource.Success) {
            val transactionResponse = response.data
            val transaction = DataMapper.transactionResponseToTransaction(transactionResponse)
            return Resource.Success(transaction)
        }
        //try another id
        // TODO
        Log.e("apicall", (response as Resource.Error).exception.message.toString())
        return response as Resource.Error
    }
}