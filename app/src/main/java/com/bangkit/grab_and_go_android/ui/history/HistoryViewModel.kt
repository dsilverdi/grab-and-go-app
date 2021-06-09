package com.bangkit.grab_and_go_android.ui.history

import androidx.lifecycle.*
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.source.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val history: LiveData<List<Cart>>
        get() = liveData {
            emit(cartRepository.getShoppingHIstory())
        }

}