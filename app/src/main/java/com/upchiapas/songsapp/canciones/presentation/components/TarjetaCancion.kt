package com.upchiapas.songsapp.canciones.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.upchiapas.songsapp.canciones.domain.model.Cancion
import com.upchiapas.songsapp.ui.theme.AzulSuave
import com.upchiapas.songsapp.ui.theme.BordeSuave
import com.upchiapas.songsapp.ui.theme.Superficie

@Composable
fun TarjetaCancion(
    cancion: Cancion,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datos = cancion.datos
    val forma = RoundedCornerShape(16.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(forma)
            .border(1.dp, BordeSuave, forma)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AzulSuave),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = datos.tonalidad,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = datos.titulo, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = datos.artista,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Etiqueta(texto = "${datos.bpm} BPM")
                Etiqueta(texto = "${formatearDuracion(datos.duracionMin)} min")
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconoEstado(estado = datos.estado)
    }
}

@Composable
private fun Etiqueta(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Superficie)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

fun formatearDuracion(minutos: Double): String =
    if (minutos % 1.0 == 0.0) minutos.toInt().toString() else minutos.toString()
