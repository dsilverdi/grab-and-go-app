package com.bangkit.grab_and_go_android.data.source

import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.data.source.remote.UsersRemoteDataSource
import com.bangkit.grab_and_go_android.data.vo.Resource
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource
) {

    fun getCurrentUser(): Resource<User> {
        return usersRemoteDataSource.getCurrentUser()
    }

    suspend fun signIn(email: String, password: String): Resource<User> {
        return usersRemoteDataSource.signIn(email, password)
    }

    suspend fun signUp(user: User): Resource<String> {
        return usersRemoteDataSource.signUp(user)
    }

    suspend fun signOut(): Resource<Any?> {
        return usersRemoteDataSource.signOut()
    }
}