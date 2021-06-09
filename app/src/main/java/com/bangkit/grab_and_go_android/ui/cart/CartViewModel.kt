package com.bangkit.grab_and_go_android.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.Transaction
import com.bangkit.grab_and_go_android.data.source.CartRepository
import com.bangkit.grab_and_go_android.data.source.PaymentRepository
import com.bangkit.grab_and_go_android.data.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _goToCheckout =  MutableLiveData<Boolean>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    fun getCartItems(image: ByteArray) {
        setLoading(true)
        viewModelScope.launch {
            when(val response = cartRepository.getCartItems(image)) {
                is Resource.Success -> _cart.value = response.data!!
            }
            setLoading(false)
        }
    }

    private val _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction> = _transaction

    fun checkout() {
        setLoading(true)
        viewModelScope.launch {
            _cart.value?.let {
                when(val response = paymentRepository.checkout(it)) {
                    is Resource.Success -> {
                        _transaction.value = response.data!!
                    }
                }
            }
            setLoading(false)
        }
    }

    private fun setLoading(value: Boolean) {
        _loading.value = value
    }

}