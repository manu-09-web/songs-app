package com.upchiapas.songsapp.auth.data.datasource

import com.upchiapas.songsapp.auth.data.dto.AuthResponseDto
import com.upchiapas.songsapp.auth.data.dto.LoginRequestDto
import com.upchiapas.songsapp.auth.data.dto.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("register")
    suspend fun registrar(@Body body: RegisterRequestDto): AuthResponseDto

    @POST("login")
    suspend fun iniciarSesion(@Body body: LoginRequestDto): AuthResponseDto
}
