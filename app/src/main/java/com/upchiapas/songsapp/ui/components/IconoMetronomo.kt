package com.upchiapas.songsapp.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconoMetronomo: ImageVector = ImageVector.Builder(
    name = "Metronomo",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 1.8f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(6f, 20f)
        lineTo(9f, 4f)
        horizontalLineTo(15f)
        lineTo(18f, 20f)
        close()
        moveTo(12f, 16f)
        lineTo(17f, 6f)
    }
}.build()
