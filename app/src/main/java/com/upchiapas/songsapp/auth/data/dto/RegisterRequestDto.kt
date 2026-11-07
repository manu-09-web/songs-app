package com.upchiapas.songsapp.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    @SerialName("nombre") val nombre: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)
