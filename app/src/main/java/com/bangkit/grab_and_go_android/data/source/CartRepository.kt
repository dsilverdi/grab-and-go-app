package com.bangkit.grab_and_go_android.data.source

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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
//    suspend fun getCartItems(imgBase64: String): Resource<Cart> {
//        val response = cartRemoteDataSource.getCartItems(imgBase64)
//        if (response is Resource.Success) {
//            val cartResponse = response.data
//            val cart = DataMapper.cartResponseToCart(cartResponse)
//            return Resource.Success(cart)
//        }
//        Log.e("apicall", (response as Resource.Error).exception.message.toString())
//        return response as Resource.Error
//    }

    suspend fun getCartItems(image: ByteArray): Resource<Cart> {
        val imgBase64: String = convertImageToBase64(image)
        val response = cartRemoteDataSource.getCartItems(imgBase64)
        if (response is Resource.Success) {
            val cartResponse = response.data
            val cart = DataMapper.cartResponseToCart(cartResponse)
            return Resource.Success(cart)
        }
        Log.e("apicall", (response as Resource.Error).exception.message.toString())
        return response as Resource.Error
    }

    private fun compressImage() {
//        BitmapFactory.
    }

    private fun convertImageToBase64(image: ByteArray): String {
        return Base64.encodeToString(image, Base64.DEFAULT)
    }

    fun getShoppingHIstory(): List<Cart> {
        return cartLocalDataSource.getShoppingHistory()
    }

}