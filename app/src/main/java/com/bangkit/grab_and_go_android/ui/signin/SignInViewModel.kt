package com.bangkit.grab_and_go_android.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.data.source.UsersRepository
import com.bangkit.grab_and_go_android.data.vo.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _loginSuccess = MutableLiveData<Boolean>(false)
    val loginSuccess: LiveData<Boolean> =_loginSuccess
    private val _loginFailed = MutableLiveData<Boolean>(false)
    val loginFailed: LiveData<Boolean> =_loginFailed
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser

    fun authenticate(email: String, password: String) {
        _loading.value = true
        _loginSuccess.value = false
        _loginFailed.value = false
        viewModelScope.launch {
            when(val task = usersRepository.signIn(email, password)) {
                is Response.Success -> {
                    _loginSuccess.value = true
                    _currentUser.value = task.data
                }
                is Response.Error -> {
                    _loginFailed.value = true
                }
            }
            _loading.value = false
        }
    }

    fun getSignedInUser(): LiveData<User> {
        _loading.value = true
        viewModelScope.launch {
            when(val response = usersRepository.getCurrentUser()) {
                is Response.Success -> {
                    _loginSuccess.value = true
                    _currentUser.value = response.data!!
                }
                else -> {

                }
            }
            _loading.value = false
        }
        return currentUser
    }

}