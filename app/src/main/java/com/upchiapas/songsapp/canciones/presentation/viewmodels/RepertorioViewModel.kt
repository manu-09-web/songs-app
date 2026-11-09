package com.upchiapas.songsapp.canciones.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.upchiapas.songsapp.SongsApplication
import com.upchiapas.songsapp.auth.domain.repository.AuthRepository
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import com.upchiapas.songsapp.canciones.domain.repository.CancionRepository
import com.upchiapas.songsapp.canciones.presentation.screens.RepertorioUiState
import com.upchiapas.songsapp.core.network.ApiException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime

class RepertorioViewModel(
    private val cancionRepository: CancionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(estadoInicial())
    val uiState: StateFlow<RepertorioUiState> = _uiState.asStateFlow()

    fun cargarCanciones() {
        _uiState.update { it.copy(cargando = it.canciones.isEmpty(), error = null) }
        viewModelScope.launch {
            cancionRepository.listar()
                .onSuccess { canciones ->
                    _uiState.update { it.copy(cargando = false, canciones = canciones) }
                }
                .onFailure { error ->
                    val codigo = (error as? ApiException)?.codigo
                    if (codigo == "NO_AUTENTICADO") {
                        authRepository.cerrarSesion()
                        _uiState.update { it.copy(cargando = false, sesionVencida = true) }
                    } else {
                        _uiState.update {
                            it.copy(
                                cargando = false,
                                error = error.message,
                                errorSinConexion = codigo == "SIN_CONEXION"
                            )
                        }
                    }
                }
        }
    }

    fun onFiltroChange(estado: EstadoCancion?) {
        _uiState.update { it.copy(filtro = estado) }
    }

    fun cerrarSesion() {
        authRepository.cerrarSesion()
        _uiState.update { it.copy(sesionCerrada = true) }
    }

    private fun estadoInicial(): RepertorioUiState {
        val nombre = authRepository.nombreUsuario().orEmpty()
        return RepertorioUiState(
            saludo = saludoSegunHora(LocalTime.now().hour),
            nombreUsuario = nombre,
            iniciales = nombre.split(" ").filter { it.isNotBlank() }.take(2)
                .joinToString("") { it.first().uppercase() }
        )
    }

    private fun saludoSegunHora(hora: Int): String = when (hora) {
        in 5..11 -> "Buenos días,"
        in 12..18 -> "Buenas tardes,"
        else -> "Buenas noches,"
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SongsApplication
                RepertorioViewModel(app.container.cancionRepository, app.container.authRepository)
            }
        }
    }
}
