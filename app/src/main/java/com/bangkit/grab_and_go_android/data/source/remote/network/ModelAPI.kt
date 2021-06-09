package com.bangkit.grab_and_go_android.data.source.remote.network

import com.bangkit.grab_and_go_android.data.Checkout
import com.bangkit.grab_and_go_android.data.source.remote.request.CheckoutRequest
import com.bangkit.grab_and_go_android.data.source.remote.response.CartItemResponse
import com.bangkit.grab_and_go_android.data.source.remote.response.CartResponse
import com.bangkit.grab_and_go_android.data.source.remote.response.TransactionResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ModelAPI {

    @POST("/detection")
    suspend fun getModelResult(@Body img: RequestBody): Response<CartResponse>

//    @Headers("Content-Type: application/json")
//    @POST("/payment")
//    suspend fun checkout(@Body checkoutRequest: CheckoutRequest): Response<TransactionResponse>

    @Headers("Content-Type: application/json")
    @POST("/payment")
    suspend fun checkout(@Body checkoutDetail: RequestBody): Response<TransactionResponse>

}