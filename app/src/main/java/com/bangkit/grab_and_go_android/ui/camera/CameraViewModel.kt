package com.bangkit.grab_and_go_android.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.source.CartRepository
import com.bangkit.grab_and_go_android.data.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _cart = MutableLiveData<Cart?>()
    val cart: LiveData<Cart?> = _cart

    private val _previewState = MutableLiveData<Boolean>()
    val previewState: LiveData<Boolean> = _previewState

    private val _cartNavState = MutableLiveData<Boolean>()
    val cartNavState: LiveData<Boolean> = _cartNavState

    fun goToPreview(value: Boolean) {
        viewModelScope.launch {
            _previewState.value = value
        }
    }

    fun goToCartFragment(value: Boolean) {
        viewModelScope.launch {
            _cartNavState.value = value
        }
    }

    fun getResult(imgBase64: String) {
        viewModelScope.launch {
            prepare()
            when(val response = cartRepository.getCartItems(imgBase64)) {
                is Resource.Success -> {
                    _cart.value = response.data
                }
            }
            _loading.value = false
        }
    }

    private fun prepare() {
        if(_loading.value == false) {
            _loading.value = true
        }
        _cart.value = null
    }

    fun setLoading() {
        viewModelScope.launch {
            _loading.value = true
        }
    }
    fun setLoadingDone() {
        viewModelScope.launch {
            _loading.value = false
        }
    }

}