package com.upchiapas.songsapp.canciones.data.repository

import com.upchiapas.songsapp.canciones.data.datasource.CancionApiService
import com.upchiapas.songsapp.canciones.data.mapper.toDomain
import com.upchiapas.songsapp.canciones.data.mapper.toRequestDto
import com.upchiapas.songsapp.canciones.domain.model.Cancion
import com.upchiapas.songsapp.canciones.domain.model.DatosCancion
import com.upchiapas.songsapp.canciones.domain.repository.CancionRepository
import com.upchiapas.songsapp.core.network.llamarApi
import retrofit2.HttpException

class CancionRepositoryImpl(
    private val api: CancionApiService
) : CancionRepository {

    override suspend fun listar(): Result<List<Cancion>> =
        llamarApi { api.listar().toDomain() }

    override suspend fun obtener(id: Int): Result<Cancion> =
        llamarApi { api.obtener(id).toDomain() }

    override suspend fun crear(datos: DatosCancion): Result<Cancion> =
        llamarApi { api.crear(datos.toRequestDto()).toDomain() }

    override suspend fun editar(id: Int, datos: DatosCancion): Result<Cancion> =
        llamarApi { api.reemplazar(id, datos.toRequestDto()).toDomain() }

    override suspend fun eliminar(id: Int): Result<Unit> =
        llamarApi {
            val respuesta = api.eliminar(id)
            if (!respuesta.isSuccessful) throw HttpException(respuesta)
        }
}
