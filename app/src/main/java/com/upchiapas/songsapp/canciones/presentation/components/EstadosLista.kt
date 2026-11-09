package com.upchiapas.songsapp.canciones.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upchiapas.songsapp.ui.theme.BordeSuave

@Composable
fun MensajeLista(
    icono: ImageVector,
    colorIcono: Color,
    fondoIcono: Color,
    titulo: String,
    mensaje: String,
    accion: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(fondoIcono),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = titulo, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        accion()
    }
}

@Composable
fun BotonContorno(texto: String, icono: ImageVector, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Icon(imageVector = icono, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = texto, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun ResumenCargando() {
    Bloque(alto = 24.dp, ancho = 0.6f, modifier = Modifier.padding(vertical = 4.dp))
    Spacer(modifier = Modifier.height(14.dp))
    Bloque(alto = 8.dp, ancho = 1f)
    Spacer(modifier = Modifier.height(10.dp))
    Bloque(alto = 12.dp, ancho = 0.5f)
}

@Composable
fun CancionCargando(modifier: Modifier = Modifier) {
    val forma = RoundedCornerShape(16.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, BordeSuave, forma)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BordeSuave)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Bloque(alto = 14.dp, ancho = 0.7f)
            Spacer(modifier = Modifier.height(10.dp))
            Bloque(alto = 12.dp, ancho = 0.4f)
        }
    }
}

@Composable
private fun Bloque(alto: Dp, ancho: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(ancho)
            .height(alto)
            .clip(RoundedCornerShape(6.dp))
            .background(BordeSuave)
    )
}
