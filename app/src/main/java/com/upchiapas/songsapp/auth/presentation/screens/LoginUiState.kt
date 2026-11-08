package com.upchiapas.songsapp.auth.presentation.screens

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val errorEmail: String? = null,
    val errorPassword: String? = null,
    val errorGeneral: String? = null,
    val cargando: Boolean = false,
    val sesionIniciada: Boolean = false
)
