package com.bangkit.grab_and_go_android.data.source.remote

import com.bangkit.grab_and_go_android.data.source.remote.network.ModelAPI
import com.bangkit.grab_and_go_android.data.source.remote.response.CartItemResponse
import com.bangkit.grab_and_go_android.data.source.remote.response.CartResponse
import com.bangkit.grab_and_go_android.data.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject


class CartRemoteDataSource @Inject constructor (
    private val api: ModelAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getCartItems(imgBase64: String): Resource<CartResponse> = withContext(ioDispatcher) {
//        try {
//            val response = api.getModelResult(imgBase64)
//            return@withContext Resource.Success()
//        } catch(e: Exception) {
//            return@withContext Resource.Error(e)
//        }
        val type = "text/plain".toMediaTypeOrNull()
        val body: RequestBody = imgBase64.toRequestBody(type)
        val response = api.getModelResult(body)
        return@withContext apiResponseHandler(response)
    }

    private fun <T> apiResponseHandler(response: Response<T>): Resource<T> {
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result as T)
            }
        }
        return Resource.Error(Exception(response.message()))
    }

}
