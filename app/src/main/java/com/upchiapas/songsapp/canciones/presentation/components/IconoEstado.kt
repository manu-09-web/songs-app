package com.upchiapas.songsapp.canciones.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import com.upchiapas.songsapp.ui.theme.Ambar
import com.upchiapas.songsapp.ui.theme.AmbarSuave
import com.upchiapas.songsapp.ui.theme.AmbarTexto
import com.upchiapas.songsapp.ui.theme.Blanco
import com.upchiapas.songsapp.ui.theme.GrisEstado
import com.upchiapas.songsapp.ui.theme.Superficie
import com.upchiapas.songsapp.ui.theme.TextoSecundario
import com.upchiapas.songsapp.ui.theme.Verde
import com.upchiapas.songsapp.ui.theme.VerdeSuave
import com.upchiapas.songsapp.ui.theme.VerdeTexto

fun EstadoCancion.color(): Color = when (this) {
    EstadoCancion.DOMINADA -> Verde
    EstadoCancion.A_MEDIAS -> Ambar
    EstadoCancion.NO_DOMINADA -> GrisEstado
}

fun EstadoCancion.colorFondo(): Color = when (this) {
    EstadoCancion.DOMINADA -> VerdeSuave
    EstadoCancion.A_MEDIAS -> AmbarSuave
    EstadoCancion.NO_DOMINADA -> Superficie
}

fun EstadoCancion.colorTexto(): Color = when (this) {
    EstadoCancion.DOMINADA -> VerdeTexto
    EstadoCancion.A_MEDIAS -> AmbarTexto
    EstadoCancion.NO_DOMINADA -> TextoSecundario
}

@Composable
fun IconoEstado(estado: EstadoCancion, modifier: Modifier = Modifier, tamano: Dp = 22.dp) {
    val color = estado.color()
    Canvas(modifier = modifier.size(tamano)) {
        val grosor = 1.8.dp.toPx()
        val radio = size.minDimension / 2 - grosor / 2
        when (estado) {
            EstadoCancion.DOMINADA -> {
                drawCircle(color = color, radius = size.minDimension / 2)
                val palomita = Path().apply {
                    moveTo(size.width * 0.28f, size.height * 0.52f)
                    lineTo(size.width * 0.44f, size.height * 0.67f)
                    lineTo(size.width * 0.73f, size.height * 0.36f)
                }
                drawPath(palomita, color = Blanco, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
            }
            EstadoCancion.A_MEDIAS -> {
                drawCircle(color = color, radius = radio, style = Stroke(width = grosor))
                drawArc(
                    color = color,
                    startAngle = 90f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(grosor / 2, grosor / 2),
                    size = Size(size.width - grosor, size.height - grosor)
                )
            }
            EstadoCancion.NO_DOMINADA -> drawCircle(color = color, radius = radio, style = Stroke(width = grosor))
        }
    }
}
