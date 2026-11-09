package com.upchiapas.songsapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upchiapas.songsapp.ui.theme.Texto

@Composable
fun AvisoHost(estado: SnackbarHostState, modifier: Modifier = Modifier) {
    SnackbarHost(hostState = estado, modifier = modifier) { datos ->
        Snackbar(
            snackbarData = datos,
            shape = RoundedCornerShape(12.dp),
            containerColor = Texto,
            actionColor = Color(0xFF93C5FD)
        )
    }
}

@Composable
fun MostrarAviso(mensaje: String?, estado: SnackbarHostState, onMostrado: () -> Unit) {
    LaunchedEffect(mensaje) {
        if (mensaje != null) {
            onMostrado()
            estado.showSnackbar(mensaje, actionLabel = "OK", duration = SnackbarDuration.Short)
        }
    }
}
