package com.upchiapas.songsapp.canciones.data.datasource

import com.upchiapas.songsapp.canciones.data.dto.CancionDto
import com.upchiapas.songsapp.canciones.data.dto.CancionRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CancionApiService {

    @GET("canciones")
    suspend fun listar(): List<CancionDto>

    @GET("canciones/{id}")
    suspend fun obtener(@Path("id") id: Int): CancionDto

    @POST("canciones")
    suspend fun crear(@Body body: CancionRequestDto): CancionDto

    @PUT("canciones/{id}")
    suspend fun reemplazar(@Path("id") id: Int, @Body body: CancionRequestDto): CancionDto

    @DELETE("canciones/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
