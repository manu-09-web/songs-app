package com.upchiapas.songsapp.core.session

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesSessionManager(context: Context) : SessionManager {

    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    override fun guardarSesion(token: String, nombre: String) {
        prefs.edit {
            putString(KEY_TOKEN, token)
            putString(KEY_NOMBRE, nombre)
        }
    }

    override fun obtenerToken(): String? = prefs.getString(KEY_TOKEN, null)

    override fun obtenerNombre(): String? = prefs.getString(KEY_NOMBRE, null)

    override fun cerrarSesion() {
        prefs.edit { clear() }
    }

    private companion object {
        const val KEY_TOKEN = "token"
        const val KEY_NOMBRE = "nombre"
    }
}
