package com.upchiapas.songsapp.canciones.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancionDto(
    @SerialName("id") val id: Int,
    @SerialName("titulo") val titulo: String,
    @SerialName("artista") val artista: String,
    @SerialName("tonalidad") val tonalidad: String,
    @SerialName("bpm") val bpm: Int,
    @SerialName("duracionMin") val duracionMin: Double,
    @SerialName("fechaEnsayo") val fechaEnsayo: String,
    @SerialName("estado") val estado: String,
    @SerialName("acordes") val acordes: List<String>,
    @SerialName("letra") val letra: String? = null
)
