package com.bangkit.grab_and_go_android.ui

import androidx.lifecycle.ViewModel
import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.data.source.UsersRepository
import com.bangkit.grab_and_go_android.data.vo.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private var _currentUser: User? = null

    fun getSignedInUser(): User? {
        _currentUser = null
        when(val response = usersRepository.getCurrentUser()) {
            is Response.Success -> {
                _currentUser = response.data
            }
            else -> {}
        }
        return _currentUser
    }

}