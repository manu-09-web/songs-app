package com.upchiapas.songsapp.auth.domain.validation

object ValidadorAuth {

    private val EMAIL = Regex("""^[^@\s]+@[^@\s]+\.[^@\s]+$""")

    fun nombre(valor: String): String? = when {
        valor.isBlank() -> "Escribe tu nombre"
        valor.trim().length > 60 -> "Máximo 60 caracteres"
        else -> null
    }

    fun email(valor: String): String? = when {
        valor.isBlank() -> "Escribe tu correo"
        !EMAIL.matches(valor.trim()) -> "Correo no válido"
        else -> null
    }

    fun password(valor: String): String? =
        if (valor.length < 8) "La contraseña debe tener al menos 8 caracteres" else null

    fun confirmacion(password: String, confirmacion: String): String? =
        if (password != confirmacion) "Las contraseñas no coinciden" else null

    fun requerido(valor: String, mensaje: String): String? =
        if (valor.isBlank()) mensaje else null
}
