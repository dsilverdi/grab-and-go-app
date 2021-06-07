package com.bangkit.grab_and_go_android.utils

import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.CartItem
import com.bangkit.grab_and_go_android.data.source.remote.response.CartItemResponse
import com.bangkit.grab_and_go_android.data.source.remote.response.CartResponse

object DataMapper {
    private fun mapCartItemResponseToCartItem(cartItemResponse: CartItemResponse): CartItem {
        cartItemResponse.let {
            return CartItem(
                id = it.id,
                name = it.name,
                price = it.price,
                qty = it.quantity,
                subtotal = it.price*it.quantity
            )
        }
    }

    fun mapCartResponseToCart(cartResponse: CartResponse): Cart {
        val items = cartResponse.map { cartItemResponse ->
            mapCartItemResponseToCartItem(cartItemResponse)
        }
        val total = items.sumOf { it.subtotal }
        return Cart(
            id = -1,
            listCartItem = items,
            total = total
        )
    }
}