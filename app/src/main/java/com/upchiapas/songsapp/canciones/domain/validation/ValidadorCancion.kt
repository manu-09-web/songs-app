package com.upchiapas.songsapp.canciones.domain.validation

import java.time.LocalDate

object ValidadorCancion {

    // Nota (A-G), alteración opcional (# o b) y "m" si es menor: C, F#, Bb, Am, F#m
    private val TONALIDAD = Regex("""^[A-G][#b]?m?$""")

    // Nota, alteración, tipo (m, maj, dim, sus…), número y bajo opcional: Am, G7, Cmaj7, Dsus4, Am/G
    private val ACORDE = Regex("""^[A-G][#b]?(m|maj|min|dim|aug|sus|add)?\d{0,2}(/[A-G][#b]?)?$""")

    fun titulo(valor: String): String? =
        if (valor.isBlank() || valor.trim().length > 80) "Escribe el título" else null

    fun artista(valor: String): String? =
        if (valor.isBlank() || valor.trim().length > 60) "Escribe el artista" else null

    fun tonalidad(valor: String): String? =
        if (!TONALIDAD.matches(valor.trim())) "Tonalidad no válida (ej. Am, F#, Bb)" else null

    fun bpm(valor: String): String? {
        val numero = valor.trim().toIntOrNull()
        return if (numero == null || numero !in 40..240) "Debe estar entre 40 y 240" else null
    }

    fun duracion(valor: String): String? {
        val numero = valor.trim().toDoubleOrNull()
        return if (numero == null || numero <= 0 || numero > 20) "Duración no válida" else null
    }

    fun fechaEnsayo(valor: LocalDate?): String? = when {
        valor == null -> "Elige la fecha del último ensayo"
        valor.isAfter(LocalDate.now()) -> "La fecha no puede ser futura"
        else -> null
    }

    fun acorde(valor: String): String? =
        if (valor.length > 8 || !ACORDE.matches(valor)) "Acorde no válido: $valor" else null

    fun acordes(valores: List<String>): String? {
        if (valores.isEmpty() || valores.size > 12) return "Agrega de 1 a 12 acordes"
        return valores.firstNotNullOfOrNull { acorde(it) }
    }

    fun letra(valor: String): String? =
        if (valor.length > 5000) "La letra es demasiado larga" else null
}
