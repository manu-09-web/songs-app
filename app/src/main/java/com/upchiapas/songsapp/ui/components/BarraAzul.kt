package com.upchiapas.songsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BarraAzul(
    modifier: Modifier = Modifier,
    espacioAbajo: Dp = 64.dp,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .statusBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = espacioAbajo),
        content = contenido
    )
}

@Composable
fun TarjetaEncimada(
    modifier: Modifier = Modifier,
    contenido: @Composable ColumnScope.() -> Unit
) {
    val forma = RoundedCornerShape(20.dp)
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .subir(44.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, forma)
            .border(1.dp, MaterialTheme.colorScheme.outline, forma)
            .padding(16.dp),
        content = contenido
    )
}

// Dibuja el elemento más arriba y le resta ese espacio a su alto,
// para que quede montado sobre la barra sin dejar un hueco abajo.
private fun Modifier.subir(cantidad: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val desplazamiento = cantidad.roundToPx()
    layout(placeable.width, placeable.height - desplazamiento) {
        placeable.place(0, -desplazamiento)
    }
}
