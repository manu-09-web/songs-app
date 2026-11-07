package com.upchiapas.songsapp.core.di

import android.content.Context
import com.upchiapas.songsapp.BuildConfig
import com.upchiapas.songsapp.auth.data.datasource.AuthApiService
import com.upchiapas.songsapp.auth.data.repository.AuthRepositoryImpl
import com.upchiapas.songsapp.auth.domain.repository.AuthRepository
import com.upchiapas.songsapp.core.network.AuthInterceptor
import com.upchiapas.songsapp.core.session.SessionManager
import com.upchiapas.songsapp.core.session.SharedPreferencesSessionManager
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(context: Context) {

    val sessionManager: SessionManager by lazy {
        SharedPreferencesSessionManager(context)
    }

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .addInterceptor(logging)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(retrofit.create(AuthApiService::class.java), sessionManager)
    }
}
