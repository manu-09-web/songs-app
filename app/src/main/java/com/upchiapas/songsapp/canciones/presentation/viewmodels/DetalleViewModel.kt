package com.upchiapas.songsapp.canciones.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.upchiapas.songsapp.SongsApplication
import com.upchiapas.songsapp.auth.domain.repository.AuthRepository
import com.upchiapas.songsapp.canciones.domain.repository.CancionRepository
import com.upchiapas.songsapp.canciones.presentation.screens.DetalleUiState
import com.upchiapas.songsapp.core.navigation.DetalleRuta
import com.upchiapas.songsapp.core.network.ApiException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalleViewModel(
    private val cancionId: Int,
    private val cancionRepository: CancionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleUiState())
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    fun cargar() {
        _uiState.update { it.copy(cargando = it.cancion == null, error = null) }
        viewModelScope.launch {
            cancionRepository.obtener(cancionId)
                .onSuccess { cancion -> _uiState.update { it.copy(cargando = false, cancion = cancion) } }
                .onFailure { error ->
                    if (sesionVencida(error)) return@onFailure
                    _uiState.update { it.copy(cargando = false, error = error.message) }
                }
        }
    }

    fun pedirEliminar() {
        _uiState.update { it.copy(mostrarConfirmacion = true, errorAlEliminar = null) }
    }

    fun cancelarEliminar() {
        _uiState.update { it.copy(mostrarConfirmacion = false) }
    }

    fun eliminar() {
        _uiState.update { it.copy(mostrarConfirmacion = false, eliminando = true) }
        viewModelScope.launch {
            cancionRepository.eliminar(cancionId)
                .onSuccess { _uiState.update { it.copy(eliminando = false, eliminada = true) } }
                .onFailure { error ->
                    if (sesionVencida(error)) return@onFailure
                    _uiState.update { it.copy(eliminando = false, errorAlEliminar = error.message) }
                }
        }
    }

    fun limpiarErrorAlEliminar() {
        _uiState.update { it.copy(errorAlEliminar = null) }
    }

    private fun sesionVencida(error: Throwable): Boolean {
        if ((error as? ApiException)?.codigo != "NO_AUTENTICADO") return false
        authRepository.cerrarSesion()
        _uiState.update { it.copy(cargando = false, eliminando = false, sesionVencida = true) }
        return true
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SongsApplication
                val ruta = createSavedStateHandle().toRoute<DetalleRuta>()
                DetalleViewModel(ruta.id, app.container.cancionRepository, app.container.authRepository)
            }
        }
    }
}
