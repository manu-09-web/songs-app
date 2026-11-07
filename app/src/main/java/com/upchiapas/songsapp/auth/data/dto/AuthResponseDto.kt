package com.upchiapas.songsapp.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("tokenType") val tokenType: String,
    @SerialName("usuario") val usuario: UsuarioDto
)
