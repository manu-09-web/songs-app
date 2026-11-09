package com.upchiapas.songsapp.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data class LoginRuta(val sesionVencida: Boolean = false)

@Serializable
object RegistroRuta

@Serializable
object RepertorioRuta
