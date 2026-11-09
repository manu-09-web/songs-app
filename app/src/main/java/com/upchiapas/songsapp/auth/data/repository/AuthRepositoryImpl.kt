package com.upchiapas.songsapp.auth.data.repository

import com.upchiapas.songsapp.auth.data.datasource.AuthApiService
import com.upchiapas.songsapp.auth.data.dto.AuthResponseDto
import com.upchiapas.songsapp.auth.data.dto.LoginRequestDto
import com.upchiapas.songsapp.auth.data.dto.RegisterRequestDto
import com.upchiapas.songsapp.auth.data.mapper.toDomain
import com.upchiapas.songsapp.auth.domain.model.Usuario
import com.upchiapas.songsapp.auth.domain.repository.AuthRepository
import com.upchiapas.songsapp.core.network.llamarApi
import com.upchiapas.songsapp.core.session.SessionManager
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val sessionManager: SessionManager,
    private val json: Json
) : AuthRepository {

    override suspend fun iniciarSesion(email: String, password: String): Result<Usuario> =
        llamarApi(json) { api.iniciarSesion(LoginRequestDto(email.trim(), password)) }
            .map { guardar(it) }

    override suspend fun registrar(nombre: String, email: String, password: String): Result<Usuario> =
        llamarApi(json) { api.registrar(RegisterRequestDto(nombre.trim(), email.trim(), password)) }
            .map { guardar(it) }

    override fun haySesion(): Boolean = sessionManager.obtenerToken() != null

    override fun nombreUsuario(): String? = sessionManager.obtenerNombre()

    override fun cerrarSesion() = sessionManager.cerrarSesion()

    private fun guardar(respuesta: AuthResponseDto): Usuario {
        sessionManager.guardarSesion(respuesta.accessToken, respuesta.usuario.nombre)
        return respuesta.usuario.toDomain()
    }
}
