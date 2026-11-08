package com.upchiapas.songsapp.auth.presentation.screens

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val confirmacion: String = "",
    val passwordVisible: Boolean = false,
    val errores: Map<String, String> = emptyMap(),
    val errorGeneral: String? = null,
    val cargando: Boolean = false,
    val registrado: Boolean = false
)
