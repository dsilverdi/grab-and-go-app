package com.bangkit.grab_and_go_android.data.source.remote

import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.data.vo.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val ioDispatcher: CoroutineDispatcher
) {

    private fun getUser(): FirebaseUser? = firebaseAuth.currentUser

    fun getCurrentUser(): Resource<User> {
        val firebaseUser = getUser()
        if(firebaseUser != null) {
            val uid = firebaseUser.uid
            val email = firebaseUser.email
            val name = firebaseUser.displayName
            val user = User(
                uid = uid, displayName = name, email = email
            )
            return Resource.Success(user)
        }
        return Resource.Error(Exception("Null current user"))
    }

    suspend fun signUp(user: User): Resource<String> {
        return withContext(ioDispatcher) {
            try {
                var email: String = user.email!!
                val password: String = user.password!!
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//                getUser()?.let {
//                    it.sendEmailVerification().addOnCompleteListener { emailVerificationTask ->
//                        if(!emailVerificationTask.isSuccessful) {
//                            email = ""
//                        }
//                    }.await()
//                }
                return@withContext Resource.Success(email)
            } catch (e: Exception) {
                return@withContext Resource.Error(e)
            }
        }
    }

//    fun setProfile() {
//        getUser().displayName
//    }

    suspend fun signIn(email: String, password: String): Resource<User> {
        return withContext(ioDispatcher) {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val uid = firebaseAuth.currentUser?.uid
                val user = User(
                    uid = uid,
                    email = email
                )
                return@withContext Resource.Success(user)
            } catch (e: Exception) {
                return@withContext Resource.Error(e)
            }

        }
    }

    fun signOut(): Resource<Any?> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(null)
        } catch(e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun registerUser() {

    }

    suspend fun verifyEmail() {

    }

}