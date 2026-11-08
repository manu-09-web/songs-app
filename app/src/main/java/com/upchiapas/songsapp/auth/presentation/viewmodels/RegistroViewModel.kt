package com.upchiapas.songsapp.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.upchiapas.songsapp.SongsApplication
import com.upchiapas.songsapp.auth.domain.repository.AuthRepository
import com.upchiapas.songsapp.auth.domain.validation.ValidadorAuth
import com.upchiapas.songsapp.auth.presentation.screens.RegistroUiState
import com.upchiapas.songsapp.core.network.ApiException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onNombreChange(valor: String) = actualizar("nombre") { it.copy(nombre = valor) }

    fun onEmailChange(valor: String) = actualizar("email") { it.copy(email = valor) }

    fun onPasswordChange(valor: String) = actualizar("password") { it.copy(password = valor) }

    fun onConfirmacionChange(valor: String) = actualizar("confirmacion") { it.copy(confirmacion = valor) }

    fun onCambiarVisibilidad() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun registrar() {
        val estado = _uiState.value
        val errores = listOfNotNull(
            ValidadorAuth.nombre(estado.nombre)?.let { "nombre" to it },
            ValidadorAuth.email(estado.email)?.let { "email" to it },
            ValidadorAuth.password(estado.password)?.let { "password" to it },
            ValidadorAuth.confirmacion(estado.password, estado.confirmacion)?.let { "confirmacion" to it }
        ).toMap()
        if (errores.isNotEmpty()) {
            _uiState.update { it.copy(errores = errores) }
            return
        }

        _uiState.update { it.copy(cargando = true, errorGeneral = null) }
        viewModelScope.launch {
            authRepository.registrar(estado.nombre, estado.email, estado.password)
                .onSuccess {
                    _uiState.update { it.copy(cargando = false, registrado = true) }
                }
                .onFailure { error ->
                    val api = error as? ApiException
                    val erroresServidor = when (api?.codigo) {
                        "EMAIL_EN_USO" -> mapOf("email" to api.message)
                        else -> api?.errores.orEmpty()
                    }
                    _uiState.update {
                        it.copy(
                            cargando = false,
                            errores = erroresServidor,
                            errorGeneral = if (erroresServidor.isEmpty()) error.message else null
                        )
                    }
                }
        }
    }

    private fun actualizar(campo: String, cambio: (RegistroUiState) -> RegistroUiState) {
        _uiState.update { cambio(it).copy(errores = it.errores - campo, errorGeneral = null) }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SongsApplication
                RegistroViewModel(app.container.authRepository)
            }
        }
    }
}
