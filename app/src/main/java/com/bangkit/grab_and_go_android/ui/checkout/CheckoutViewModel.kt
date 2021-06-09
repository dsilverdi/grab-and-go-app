package com.bangkit.grab_and_go_android.ui.checkout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.Transaction
import com.bangkit.grab_and_go_android.data.source.PaymentRepository
import com.bangkit.grab_and_go_android.data.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction> = _transaction

    fun checkout(cart: Cart) {
        viewModelScope.launch {
            when(val response = paymentRepository.checkout(cart)) {
                is Resource.Success -> {
                    Log.e("checkout", response.data.toString())
                }
            }
        }
    }

}