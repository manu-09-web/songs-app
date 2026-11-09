package com.upchiapas.songsapp.canciones.presentation.screens

import com.upchiapas.songsapp.canciones.domain.model.Cancion

data class DetalleUiState(
    val cargando: Boolean = true,
    val cancion: Cancion? = null,
    val error: String? = null,
    val mostrarConfirmacion: Boolean = false,
    val eliminando: Boolean = false,
    val eliminada: Boolean = false,
    val errorAlEliminar: String? = null,
    val sesionVencida: Boolean = false
)
