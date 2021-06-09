package com.bangkit.grab_and_go_android.data.vo

sealed class Resource<out R>() {

    class Success<T>(val data: T) : Resource<T>()
    class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}