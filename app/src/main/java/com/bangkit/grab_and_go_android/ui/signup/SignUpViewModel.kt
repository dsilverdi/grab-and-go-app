package com.bangkit.grab_and_go_android.ui.signup

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
class SignUpViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _errorException = MutableLiveData<Exception?>()
    val errorException: LiveData<Exception?> = _errorException

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val email = MutableLiveData<String?>()

    private fun prepare() {
        _loading.value = true
        _error.value = false
        _success.value = false
    }

    fun signUp(user: User): LiveData<String?> {
        prepare()
        viewModelScope.launch {
            val task = usersRepository.signUp(user)
            if(task is Resource.Success) {
                _success.value = true
                email.value = task.data
            } else {
                _error.value = true
                _errorException.value = (task as Resource.Error).exception
            }
            _loading.value = false

        }
        return email
    }

}