package com.upchiapas.songsapp.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.upchiapas.songsapp.SongsApplication
import com.upchiapas.songsapp.auth.domain.repository.AuthRepository
import com.upchiapas.songsapp.auth.domain.validation.ValidadorAuth
import com.upchiapas.songsapp.auth.presentation.screens.LoginUiState
import com.upchiapas.songsapp.core.network.ApiException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(email = valor, errorEmail = null, errorGeneral = null) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, errorPassword = null, errorGeneral = null) }
    }

    fun onCambiarVisibilidad() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun iniciarSesion() {
        val estado = _uiState.value
        val errorEmail = ValidadorAuth.requerido(estado.email, "Escribe tu correo")
        val errorPassword = ValidadorAuth.requerido(estado.password, "Escribe tu contraseña")
        if (errorEmail != null || errorPassword != null) {
            _uiState.update { it.copy(errorEmail = errorEmail, errorPassword = errorPassword) }
            return
        }

        _uiState.update { it.copy(cargando = true, errorGeneral = null) }
        viewModelScope.launch {
            authRepository.iniciarSesion(estado.email, estado.password)
                .onSuccess {
                    _uiState.update { it.copy(cargando = false, sesionIniciada = true) }
                }
                .onFailure { error ->
                    val errores = (error as? ApiException)?.errores.orEmpty()
                    _uiState.update {
                        it.copy(
                            cargando = false,
                            errorEmail = errores["email"],
                            errorPassword = errores["password"],
                            errorGeneral = if (errores.isEmpty()) error.message else null
                        )
                    }
                }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SongsApplication
                LoginViewModel(app.container.authRepository)
            }
        }
    }
}
