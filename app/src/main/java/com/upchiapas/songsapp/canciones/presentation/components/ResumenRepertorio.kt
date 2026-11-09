package com.upchiapas.songsapp.canciones.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import com.upchiapas.songsapp.ui.theme.BordeSuave

@Composable
fun ResumenRepertorio(total: Int, dominadas: Int, aMedias: Int) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Dato(total, "canciones", MaterialTheme.colorScheme.onSurface, Modifier.weight(1f))
        Box(modifier = Modifier.width(1.dp).height(40.dp).background(BordeSuave))
        Dato(dominadas, "dominadas", EstadoCancion.DOMINADA.colorTexto(), Modifier.weight(1f))
        Box(modifier = Modifier.width(1.dp).height(40.dp).background(BordeSuave))
        Dato(aMedias, "a medias", EstadoCancion.A_MEDIAS.colorTexto(), Modifier.weight(1f))
    }
    Spacer(modifier = Modifier.height(14.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val sinDominar = total - dominadas - aMedias
        if (total == 0) {
            Segmento(1, BordeSuave)
        } else {
            if (dominadas > 0) Segmento(dominadas, EstadoCancion.DOMINADA.color())
            if (aMedias > 0) Segmento(aMedias, EstadoCancion.A_MEDIAS.color())
            if (sinDominar > 0) Segmento(sinDominar, BordeSuave)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    val porcentaje = if (total == 0) 0 else dominadas * 100 / total
    Text(
        text = "La banda domina el $porcentaje % del repertorio",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun Dato(valor: Int, etiqueta: String, color: Color, modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor.toString(),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = color
        )
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.Segmento(peso: Int, color: Color) {
    Box(
        modifier = Modifier
            .weight(peso.toFloat())
            .fillMaxHeight()
            .background(color, RoundedCornerShape(4.dp))
    )
}
