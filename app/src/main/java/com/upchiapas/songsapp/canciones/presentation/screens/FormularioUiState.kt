package com.upchiapas.songsapp.canciones.presentation.screens

import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import java.time.LocalDate

data class FormularioUiState(
    val esEdicion: Boolean = false,
    val cargando: Boolean = false,
    val titulo: String = "",
    val artista: String = "",
    val tonalidad: String = "",
    val bpm: String = "",
    val duracion: String = "",
    val fechaEnsayo: LocalDate? = null,
    val estado: EstadoCancion = EstadoCancion.NO_DOMINADA,
    val acordes: List<String> = emptyList(),
    val acordeNuevo: String = "",
    val letra: String = "",
    val mostrarCalendario: Boolean = false,
    val errores: Map<String, String> = emptyMap(),
    val errorGeneral: String? = null,
    val guardando: Boolean = false,
    val guardada: Boolean = false,
    val sesionVencida: Boolean = false
)
