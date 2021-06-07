package com.bangkit.grab_and_go_android.data

import android.os.Parcelable
import android.text.format.DateFormat
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Cart(
    val id: Long,
    val listCartItem: List<CartItem>,
    val total: Int,
    val timestamp: Long = -1,
) : Parcelable {

    fun getTotalAsString(): String = rupiahString(total)
    private fun rupiahString(value: Number): String = "Rp${"%,d".format(value)}"

    fun getDate(): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("dd-MM-yyyy", calendar).toString()
    }
}
