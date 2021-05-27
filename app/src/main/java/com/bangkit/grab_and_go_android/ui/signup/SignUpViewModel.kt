package com.bangkit.grab_and_go_android.ui.signup

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
class SignUpViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _verificationEmailSent = MutableLiveData<Boolean>(false)
    val verificationEmailSent: LiveData<Boolean> = _verificationEmailSent

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val verifiedEmail = MutableLiveData<String?>()

    fun signUp(user: User): LiveData<String?> {
        _loading.value = true
        viewModelScope.launch {
            val task = usersRepository.signUp(user)
            if(task is Response.Success) {
                _verificationEmailSent.value = true
                verifiedEmail.value = task.data
            }
            _loading.value = false

        }
        return verifiedEmail
    }

}