package com.upchiapas.songsapp.auth.domain.repository

import com.upchiapas.songsapp.auth.domain.model.Usuario

interface AuthRepository {
    suspend fun iniciarSesion(email: String, password: String): Result<Usuario>
    suspend fun registrar(nombre: String, email: String, password: String): Result<Usuario>
    fun haySesion(): Boolean
    fun nombreUsuario(): String?
    fun cerrarSesion()
}
