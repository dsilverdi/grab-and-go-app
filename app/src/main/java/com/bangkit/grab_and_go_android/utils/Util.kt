package com.bangkit.grab_and_go_android.utils

import androidx.annotation.DrawableRes
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.vo.Resource
import retrofit2.Response
import kotlin.random.Random

object Util {

    fun Int.toRupiahString(): String {
        return "Rp${"%,d".format(this)}"
    }

    fun randomId(): Long = Random.nextLong(100, 1_000_000_000)

    fun <T> apiResponseHandler(response: Response<T>): Resource<T> {
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result as T)
            }
        }
        return Resource.Error(Exception(response.message()))
    }

    @DrawableRes
    fun getBackButtonIconRes(): Int {
        return R.drawable.ic_round_arrow_back_ios_new_24
    }


}