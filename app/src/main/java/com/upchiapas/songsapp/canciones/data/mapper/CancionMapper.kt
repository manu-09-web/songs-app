package com.upchiapas.songsapp.canciones.data.mapper

import com.upchiapas.songsapp.canciones.data.dto.CancionDto
import com.upchiapas.songsapp.canciones.data.dto.CancionRequestDto
import com.upchiapas.songsapp.canciones.domain.model.Cancion
import com.upchiapas.songsapp.canciones.domain.model.DatosCancion
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import java.time.LocalDate

fun CancionDto.toDomain(): Cancion = Cancion(
    id = id,
    datos = DatosCancion(
        titulo = titulo,
        artista = artista,
        tonalidad = tonalidad,
        bpm = bpm,
        duracionMin = duracionMin,
        fechaEnsayo = LocalDate.parse(fechaEnsayo),
        estado = estado.toEstado(),
        acordes = acordes,
        letra = letra
    )
)

fun List<CancionDto>.toDomain(): List<Cancion> = map { it.toDomain() }

fun DatosCancion.toRequestDto(): CancionRequestDto = CancionRequestDto(
    titulo = titulo.trim(),
    artista = artista.trim(),
    tonalidad = tonalidad.trim(),
    bpm = bpm,
    duracionMin = duracionMin,
    fechaEnsayo = fechaEnsayo.toString(),
    estado = estado.toApi(),
    acordes = acordes,
    letra = letra?.takeIf { it.isNotBlank() }
)

private fun String.toEstado(): EstadoCancion = when (this) {
    "dominada" -> EstadoCancion.DOMINADA
    "a_medias" -> EstadoCancion.A_MEDIAS
    else -> EstadoCancion.NO_DOMINADA
}

private fun EstadoCancion.toApi(): String = when (this) {
    EstadoCancion.DOMINADA -> "dominada"
    EstadoCancion.A_MEDIAS -> "a_medias"
    EstadoCancion.NO_DOMINADA -> "no_dominada"
}
