package com.bangkit.grab_and_go_android.data.source

import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.data.source.remote.UsersRemoteDataSource
import com.bangkit.grab_and_go_android.data.vo.Response
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource
) {

    suspend fun getCurrentUser(): Response<User> {
        return usersRemoteDataSource.getCurrentUser()
    }

    suspend fun signIn(email: String, password: String): Response<User> {
        return usersRemoteDataSource.signIn(email, password)
    }

    suspend fun signUp(user: User): Response<String> {
        return usersRemoteDataSource.signUp(user)
    }
}