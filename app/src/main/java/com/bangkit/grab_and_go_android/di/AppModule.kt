package com.bangkit.grab_and_go_android.di

import com.bangkit.grab_and_go_android.data.source.CartRepository
import com.bangkit.grab_and_go_android.data.source.UsersRepository
import com.bangkit.grab_and_go_android.data.source.local.CartLocalDataSource
import com.bangkit.grab_and_go_android.data.source.remote.CartRemoteDataSource
import com.bangkit.grab_and_go_android.data.source.remote.UsersRemoteDataSource
import com.bangkit.grab_and_go_android.data.source.remote.network.ModelAPI
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideModelApi(): ModelAPI {
        val baseUrl = "http://34.101.176.94/"
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val headersInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("User-Agent", "Android")
                .build()
            chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(headersInterceptor)
            addInterceptor(loggingInterceptor)
            connectTimeout(300, TimeUnit.SECONDS)
            readTimeout(300, TimeUnit.SECONDS)
        }.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ModelAPI::class.java)
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
    fun provideCartLocalDataSource(
        ioDispatcher: CoroutineDispatcher
    ): CartLocalDataSource {
        return CartLocalDataSource()
    }
    @Singleton
    @Provides
    fun provideCartRemoteDataSource(
        api: ModelAPI,
        ioDispatcher: CoroutineDispatcher
    ): CartRemoteDataSource {
        return CartRemoteDataSource(
            api, ioDispatcher
        )
    }
    @Singleton
    @Provides
    fun provideCartRepository(
        cartLocalDataSource: CartLocalDataSource,
        cartRemoteDataSource: CartRemoteDataSource
    ): CartRepository {
        return CartRepository(
            cartLocalDataSource, cartRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}