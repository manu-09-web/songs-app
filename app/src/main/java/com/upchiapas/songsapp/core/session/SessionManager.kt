package com.upchiapas.songsapp.core.session

interface SessionManager {
    fun guardarSesion(token: String, nombre: String)
    fun obtenerToken(): String?
    fun obtenerNombre(): String?
    fun cerrarSesion()
}
