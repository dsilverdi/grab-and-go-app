package com.bangkit.grab_and_go_android.data.source.remote

import com.bangkit.grab_and_go_android.data.source.remote.network.ModelAPI
import com.bangkit.grab_and_go_android.data.source.remote.response.TransactionResponse
import com.bangkit.grab_and_go_android.data.vo.Resource
import com.bangkit.grab_and_go_android.utils.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject

class PaymentRemoteDataSource @Inject constructor (
    private val api: ModelAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun checkout(body: RequestBody): Resource<TransactionResponse> = withContext(ioDispatcher) {
//        val type = "application/json".toMediaTypeOrNull()
//        val body: RequestBody = checkoutDetail.toRequestBody(type)
//        Gson().toJson(response.userpatients)
        val response = api.checkout(body)
        return@withContext Util.apiResponseHandler(response)
    }
}