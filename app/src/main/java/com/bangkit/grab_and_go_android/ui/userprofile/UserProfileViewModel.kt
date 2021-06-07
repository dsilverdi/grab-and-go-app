package com.bangkit.grab_and_go_android.ui.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.data.source.UsersRepository
import com.bangkit.grab_and_go_android.data.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _logoutSuccess = MutableLiveData<Boolean>(false)
    val logoutSuccess: LiveData<Boolean> =_logoutSuccess

    private val _logoutError = MutableLiveData<Boolean>(false)
    val logoutError: LiveData<Boolean> =_logoutError

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private fun prepare() {
        _logoutError.value = false
        _logoutSuccess.value = false
        _loading.value = true
    }

    fun signOut() {
        prepare()
        viewModelScope.launch {
            when(usersRepository.signOut()) {
                is Resource.Success -> {
                    _logoutSuccess.value = true
                }
                else -> {
                    _logoutError.value = true
                }
            }
            _loading.value = false
        }
    }

    fun getSignedInUser(): LiveData<User?> {
        prepare()
        viewModelScope.launch {
            when(val response = usersRepository.getCurrentUser()) {
                is Resource.Success -> {
                    _currentUser.value = response.data
                }
                else -> {}
            }
            _loading.value = false
        }
        return _currentUser
    }
}