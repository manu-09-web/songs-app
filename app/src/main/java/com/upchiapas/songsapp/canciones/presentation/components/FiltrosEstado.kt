package com.upchiapas.songsapp.canciones.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import com.upchiapas.songsapp.ui.theme.Blanco

private val opciones = listOf(
    null to "Todas",
    EstadoCancion.DOMINADA to "Dominadas",
    EstadoCancion.A_MEDIAS to "A medias",
    EstadoCancion.NO_DOMINADA to "Sin dominar"
)

@Composable
fun FiltrosEstado(
    seleccionado: EstadoCancion?,
    total: Int,
    onSeleccionar: (EstadoCancion?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(opciones) { (estado, texto) ->
            val activo = estado == seleccionado
            val forma = RoundedCornerShape(16.dp)
            Text(
                text = if (estado == null) "$texto · $total" else texto,
                style = MaterialTheme.typography.labelSmall,
                color = if (activo) Blanco else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(forma)
                    .background(if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                    .border(1.dp, if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, forma)
                    .clickable { onSeleccionar(estado) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            )
        }
    }
}
