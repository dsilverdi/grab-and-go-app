package com.bangkit.grab_and_go_android.utils

import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.CartItem
import com.bangkit.grab_and_go_android.data.Transaction
import com.bangkit.grab_and_go_android.data.source.remote.request.CheckoutRequest
import com.bangkit.grab_and_go_android.data.source.remote.response.CartItemResponse
import com.bangkit.grab_and_go_android.data.source.remote.response.CartResponse
import com.bangkit.grab_and_go_android.data.source.remote.response.TransactionResponse
import kotlin.random.Random

object DataMapper {
    private fun cartItemResponseToCartItem(cartItemResponse: CartItemResponse): CartItem {
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

    fun cartResponseToCart(cartResponse: CartResponse): Cart {
        val items = cartResponse.map { cartItemResponse ->
            cartItemResponseToCartItem(cartItemResponse)
        }
        val total = items.sumOf { it.subtotal }
        return Cart(
            id = Util.randomId(),
            listCartItem = items,
            total = total
        )
    }

    fun cartToCheckoutRequest(cart: Cart): CheckoutRequest {
        return cart.let {
            CheckoutRequest(
                id = it.id,
                total = it.total,
                url = Constants.URI_SCHEME+"/checkout/${it.total}"
            )
        }
    }

    fun transactionResponseToTransaction(transactionResponse: TransactionResponse): Transaction {
        return transactionResponse.transactionData.let {
            Transaction(
                id = it.orderId.toLong(),
                paymentType = it.paymentType,
                total = it.grossAmount.toDoubleOrNull(),
                midtransUrl = it.actions.find { tAction ->
                    tAction.name == "deeplink-redirect"
                }?.url
            )
        }
    }
}