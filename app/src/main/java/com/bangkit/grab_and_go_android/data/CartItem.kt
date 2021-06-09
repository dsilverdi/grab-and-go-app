package com.bangkit.grab_and_go_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val id: Long,
    val name: String,
    val price: Int,
    val qty: Int,
    val subtotal: Int,
) : Parcelable {

    fun getQtyAsString(): String = "x$qty"
    fun getPriceAsString(): String = rupiahString(price)
    fun getSubtotalAsString(): String = rupiahString(subtotal)
    private fun rupiahString(value: Number): String = "Rp${"%,d".format(value)}"
}