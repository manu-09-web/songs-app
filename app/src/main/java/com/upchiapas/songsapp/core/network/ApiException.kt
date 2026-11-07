package com.upchiapas.songsapp.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

class ApiException(
    val codigo: String,
    override val message: String,
    val errores: Map<String, String> = emptyMap()
) : Exception(message)

private val json = Json { ignoreUnknownKeys = true }

suspend fun <T> llamarApi(bloque: suspend () -> T): Result<T> {
    return try {
        Result.success(bloque())
    } catch (e: HttpException) {
        Result.failure(withContext(Dispatchers.IO) { e.aApiException() })
    } catch (e: IOException) {
        Result.failure(ApiException("SIN_CONEXION", "No se pudo conectar con el servidor"))
    }
}

private fun HttpException.aApiException(): ApiException {
    val cuerpo = runCatching { response()?.errorBody()?.string() }.getOrNull()
    val error = cuerpo?.let { runCatching { json.decodeFromString<ErrorDto>(it) }.getOrNull() }
        ?: return ApiException("ERROR_INTERNO", "El servidor tuvo un problema, intenta más tarde")
    return ApiException(
        codigo = error.codigo,
        message = error.mensaje,
        errores = error.detalles.orEmpty().associate { it.campo to it.mensaje }
    )
}
