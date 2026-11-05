package com.upchiapas.songsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColoresClaros = lightColorScheme(
    primary = Azul,
    onPrimary = Blanco,
    primaryContainer = AzulSuave,
    onPrimaryContainer = Azul,
    background = Blanco,
    onBackground = Texto,
    surface = Blanco,
    onSurface = Texto,
    surfaceVariant = Superficie,
    onSurfaceVariant = TextoSecundario,
    outline = Borde,
    outlineVariant = Borde,
    error = Rojo,
    onError = Blanco,
    errorContainer = RojoSuave,
    onErrorContainer = Rojo
)

@Composable
fun SongsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColoresClaros,
        typography = Typography,
        content = content
    )
}
