package com.bangkit.grab_and_go_android.data.source.local

import com.bangkit.grab_and_go_android.data.Cart

class CartLocalDataSource() {

    fun getShoppingHistory(): List<Cart> {
        return CartHistoryDummyObject.generateDummy()
    }
}