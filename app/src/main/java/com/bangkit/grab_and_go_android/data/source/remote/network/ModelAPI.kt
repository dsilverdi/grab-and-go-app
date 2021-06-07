package com.bangkit.grab_and_go_android.data.source.remote.network

import com.bangkit.grab_and_go_android.data.source.remote.response.CartItemResponse
import com.bangkit.grab_and_go_android.data.source.remote.response.CartResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ModelAPI {
//    @POST("/detection")
//    fun getModelResult(@Body imageBase64: String): Response<List<CartItemResponse>>
    @POST("/detection")
    suspend fun getModelResult(@Body img: RequestBody): Response<CartResponse>
}