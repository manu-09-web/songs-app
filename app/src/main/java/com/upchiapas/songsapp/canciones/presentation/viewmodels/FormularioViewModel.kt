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
import com.upchiapas.songsapp.canciones.domain.model.DatosCancion
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import com.upchiapas.songsapp.canciones.domain.repository.CancionRepository
import com.upchiapas.songsapp.canciones.domain.validation.ValidadorCancion
import com.upchiapas.songsapp.canciones.presentation.components.formatearDuracion
import com.upchiapas.songsapp.canciones.presentation.screens.FormularioUiState
import com.upchiapas.songsapp.core.navigation.FormularioRuta
import com.upchiapas.songsapp.core.network.ApiException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class FormularioViewModel(
    private val cancionId: Int?,
    private val cancionRepository: CancionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormularioUiState(esEdicion = cancionId != null))
    val uiState: StateFlow<FormularioUiState> = _uiState.asStateFlow()

    init {
        if (cancionId != null) cargar(cancionId)
    }

    private fun cargar(id: Int) {
        _uiState.update { it.copy(cargando = true) }
        viewModelScope.launch {
            cancionRepository.obtener(id)
                .onSuccess { cancion ->
                    val d = cancion.datos
                    _uiState.update {
                        it.copy(
                            cargando = false,
                            titulo = d.titulo,
                            artista = d.artista,
                            tonalidad = d.tonalidad,
                            bpm = d.bpm.toString(),
                            duracion = formatearDuracion(d.duracionMin),
                            fechaEnsayo = d.fechaEnsayo,
                            estado = d.estado,
                            acordes = d.acordes,
                            letra = d.letra.orEmpty()
                        )
                    }
                }
                .onFailure { error -> manejarError(error) }
        }
    }

    fun onTituloChange(valor: String) = actualizar("titulo") { it.copy(titulo = valor) }
    fun onArtistaChange(valor: String) = actualizar("artista") { it.copy(artista = valor) }
    fun onTonalidadChange(valor: String) = actualizar("tonalidad") { it.copy(tonalidad = valor) }
    fun onBpmChange(valor: String) = actualizar("bpm") { it.copy(bpm = valor.filter { c -> c.isDigit() }.take(3)) }
    fun onDuracionChange(valor: String) = actualizar("duracionMin") { it.copy(duracion = valor.take(4)) }
    fun onEstadoChange(estado: EstadoCancion) = actualizar("estado") { it.copy(estado = estado) }
    fun onLetraChange(valor: String) = actualizar("letra") { it.copy(letra = valor) }
    fun onAcordeNuevoChange(valor: String) {
        val terminoAcorde = valor.endsWith(" ") || valor.endsWith(",")
        actualizar("acordes") { it.copy(acordeNuevo = valor.trim(' ', ',')) }
        if (terminoAcorde) agregarAcorde()
    }

    fun abrirCalendario() {
        _uiState.update { it.copy(mostrarCalendario = true) }
    }

    fun cerrarCalendario() {
        _uiState.update { it.copy(mostrarCalendario = false) }
    }

    fun onFechaChange(fecha: LocalDate) =
        actualizar("fechaEnsayo") { it.copy(fechaEnsayo = fecha, mostrarCalendario = false) }

    fun agregarAcorde(): Boolean {
        val estado = _uiState.value
        val acorde = estado.acordeNuevo
        if (acorde.isEmpty()) return true
        val error = when {
            estado.acordes.size >= 12 -> "Agrega de 1 a 12 acordes"
            else -> ValidadorCancion.acorde(acorde)
        }
        if (error != null) {
            _uiState.update { it.copy(errores = it.errores + ("acordes" to error)) }
            return false
        }
        _uiState.update { it.copy(acordes = it.acordes + acorde, acordeNuevo = "", errores = it.errores - "acordes") }
        return true
    }

    fun quitarAcorde(posicion: Int) {
        _uiState.update { it.copy(acordes = it.acordes.filterIndexed { i, _ -> i != posicion }) }
    }

    fun guardar() {
        if (!agregarAcorde()) return
        val e = _uiState.value
        val errores = listOfNotNull(
            ValidadorCancion.titulo(e.titulo)?.let { "titulo" to it },
            ValidadorCancion.artista(e.artista)?.let { "artista" to it },
            ValidadorCancion.tonalidad(e.tonalidad)?.let { "tonalidad" to it },
            ValidadorCancion.bpm(e.bpm)?.let { "bpm" to it },
            ValidadorCancion.duracion(e.duracion)?.let { "duracionMin" to it },
            ValidadorCancion.fechaEnsayo(e.fechaEnsayo)?.let { "fechaEnsayo" to it },
            ValidadorCancion.acordes(e.acordes)?.let { "acordes" to it },
            ValidadorCancion.letra(e.letra)?.let { "letra" to it }
        ).toMap()
        if (errores.isNotEmpty()) {
            _uiState.update { it.copy(errores = errores) }
            return
        }

        val datos = DatosCancion(
            titulo = e.titulo.trim(),
            artista = e.artista.trim(),
            tonalidad = e.tonalidad.trim(),
            bpm = e.bpm.toInt(),
            duracionMin = e.duracion.toDouble(),
            fechaEnsayo = e.fechaEnsayo!!,
            estado = e.estado,
            acordes = e.acordes,
            letra = e.letra.ifBlank { null }
        )
        _uiState.update { it.copy(guardando = true, errorGeneral = null) }
        viewModelScope.launch {
            val resultado = if (cancionId == null) cancionRepository.crear(datos) else cancionRepository.editar(cancionId, datos)
            resultado
                .onSuccess { _uiState.update { it.copy(guardando = false, guardada = true) } }
                .onFailure { error -> manejarError(error) }
        }
    }

    private fun manejarError(error: Throwable) {
        val api = error as? ApiException
        if (api?.codigo == "NO_AUTENTICADO") {
            authRepository.cerrarSesion()
            _uiState.update { it.copy(cargando = false, guardando = false, sesionVencida = true) }
            return
        }
        val erroresServidor = api?.errores.orEmpty()
        _uiState.update {
            it.copy(
                cargando = false,
                guardando = false,
                errores = it.errores + erroresServidor,
                errorGeneral = if (erroresServidor.isEmpty()) error.message else null
            )
        }
    }

    private fun actualizar(campo: String, cambio: (FormularioUiState) -> FormularioUiState) {
        _uiState.update { cambio(it).copy(errores = it.errores - campo, errorGeneral = null) }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SongsApplication
                val ruta = createSavedStateHandle().toRoute<FormularioRuta>()
                FormularioViewModel(ruta.id, app.container.cancionRepository, app.container.authRepository)
            }
        }
    }
}
