package com.upchiapas.songsapp.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    @SerialName("codigo") val codigo: String,
    @SerialName("mensaje") val mensaje: String,
    @SerialName("detalles") val detalles: List<DetalleErrorDto>? = null
)

@Serializable
data class DetalleErrorDto(
    @SerialName("campo") val campo: String,
    @SerialName("mensaje") val mensaje: String
)
