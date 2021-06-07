package com.bangkit.grab_and_go_android.data.source.local

import com.bangkit.grab_and_go_android.data.Cart

object CartHistoryDummyObject {
    fun generateDummy(): List<Cart> = listOf(
        Cart(
            id = 0,
            listCartItem = arrayListOf(),
            total = 80_000,
            timestamp = 1622312345
        ),
        Cart(
            id = 1,
            listCartItem = arrayListOf(),
            total = 50_000,
            timestamp = 1622424185
        ),
        Cart(
            id = 2,
            listCartItem = arrayListOf(),
            total = 100_000,
            timestamp = 1622594942
        ),
    )
}