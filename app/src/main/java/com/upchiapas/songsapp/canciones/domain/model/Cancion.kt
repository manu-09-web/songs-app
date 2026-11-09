package com.upchiapas.songsapp.canciones.domain.model

import java.time.LocalDate

data class Cancion(
    val id: Int,
    val datos: DatosCancion
)

data class DatosCancion(
    val titulo: String,
    val artista: String,
    val tonalidad: String,
    val bpm: Int,
    val duracionMin: Double,
    val fechaEnsayo: LocalDate,
    val estado: EstadoCancion,
    val acordes: List<String>,
    val letra: String?
)
