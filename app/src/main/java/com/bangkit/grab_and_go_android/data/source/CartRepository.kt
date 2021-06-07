package com.bangkit.grab_and_go_android.data.source

import android.util.Log
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.source.local.CartLocalDataSource
import com.bangkit.grab_and_go_android.data.source.remote.CartRemoteDataSource
import com.bangkit.grab_and_go_android.data.vo.Resource
import com.bangkit.grab_and_go_android.utils.DataMapper
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartLocalDataSource: CartLocalDataSource,
    private val cartRemoteDataSource: CartRemoteDataSource
) {
    suspend fun getCartItems(imgBase64: String): Resource<Cart> {
        val response = cartRemoteDataSource.getCartItems(imgBase64)
        if (response is Resource.Success) {
            val cartResponse = response.data
            val cart = DataMapper.mapCartResponseToCart(cartResponse)
            return Resource.Success(cart)
        }
        Log.e("apicall", (response as Resource.Error).exception.message.toString())
        return response as Resource.Error
    }

    fun getShoppingHIstory(): List<Cart> {
        return cartLocalDataSource.getShoppingHistory()
    }

}