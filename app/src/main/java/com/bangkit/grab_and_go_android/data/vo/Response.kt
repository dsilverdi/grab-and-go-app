package com.bangkit.grab_and_go_android.data.vo

sealed class Response<out R>() {

    class Success<T>(val data: T) : Response<T>()
    class Error(val exception: Exception) : Response<Nothing>()
    object Loading : Response<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}