package com.bangkit.grab_and_go_android.di

import com.bangkit.grab_and_go_android.data.source.UsersRepository
import com.bangkit.grab_and_go_android.data.source.remote.UsersRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Qualifier
//    @Retention(AnnotationRetention.RUNTIME)
//    annotation class RemoteUsersDataSource

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
      return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideUsersRemoteDataSource(
        firebaseAuth: FirebaseAuth,
        ioDispatcher: CoroutineDispatcher
    ): UsersRemoteDataSource {
        return UsersRemoteDataSource(
            firebaseAuth, ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideUsersRepository(
        usersRemoteDataSource: UsersRemoteDataSource
    ): UsersRepository {
        return UsersRepository(usersRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}