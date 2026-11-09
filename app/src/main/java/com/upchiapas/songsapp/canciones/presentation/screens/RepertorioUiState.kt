package com.upchiapas.songsapp.canciones.presentation.screens

import com.upchiapas.songsapp.canciones.domain.model.Cancion
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion

data class RepertorioUiState(
    val saludo: String = "",
    val nombreUsuario: String = "",
    val iniciales: String = "",
    val cargando: Boolean = true,
    val error: String? = null,
    val errorSinConexion: Boolean = false,
    val canciones: List<Cancion> = emptyList(),
    val filtro: EstadoCancion? = null,
    val sesionCerrada: Boolean = false,
    val sesionVencida: Boolean = false
) {
    val cancionesFiltradas: List<Cancion>
        get() = if (filtro == null) canciones else canciones.filter { it.datos.estado == filtro }

    val dominadas: Int
        get() = canciones.count { it.datos.estado == EstadoCancion.DOMINADA }

    val aMedias: Int
        get() = canciones.count { it.datos.estado == EstadoCancion.A_MEDIAS }

    val mostrarBotonAgregar: Boolean
        get() = !cargando && error == null && canciones.isNotEmpty()

    val tituloError: String
        get() = if (errorSinConexion) "No se pudo conectar" else "No se pudo cargar tu repertorio"

    val mensajeError: String
        get() = if (errorSinConexion) "Revisa tu internet e intenta de nuevo." else error.orEmpty()
}
