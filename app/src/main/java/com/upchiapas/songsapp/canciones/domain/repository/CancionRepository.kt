package com.upchiapas.songsapp.canciones.domain.repository

import com.upchiapas.songsapp.canciones.domain.model.Cancion
import com.upchiapas.songsapp.canciones.domain.model.DatosCancion

interface CancionRepository {
    suspend fun listar(): Result<List<Cancion>>
    suspend fun obtener(id: Int): Result<Cancion>
    suspend fun crear(datos: DatosCancion): Result<Cancion>
    suspend fun editar(id: Int, datos: DatosCancion): Result<Cancion>
    suspend fun eliminar(id: Int): Result<Unit>
}
