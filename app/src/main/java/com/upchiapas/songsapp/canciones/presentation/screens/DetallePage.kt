package com.upchiapas.songsapp.canciones.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.songsapp.canciones.domain.model.Cancion
import com.upchiapas.songsapp.canciones.presentation.components.BotonContorno
import com.upchiapas.songsapp.canciones.presentation.components.IconoEstado
import com.upchiapas.songsapp.canciones.presentation.components.MensajeLista
import com.upchiapas.songsapp.canciones.presentation.components.colorFondo
import com.upchiapas.songsapp.canciones.presentation.components.colorTexto
import com.upchiapas.songsapp.canciones.presentation.components.formatearDuracion
import com.upchiapas.songsapp.canciones.presentation.viewmodels.DetalleViewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import com.upchiapas.songsapp.ui.components.AvisoHost
import com.upchiapas.songsapp.ui.components.MostrarAviso
import com.upchiapas.songsapp.ui.components.BarraAzul
import com.upchiapas.songsapp.ui.components.BotonCuadro
import com.upchiapas.songsapp.ui.components.BotonPrincipal
import com.upchiapas.songsapp.ui.components.IconoMetronomo
import com.upchiapas.songsapp.ui.components.TarjetaEncimada
import com.upchiapas.songsapp.ui.theme.AzulSuave
import com.upchiapas.songsapp.ui.theme.AzulTexto
import com.upchiapas.songsapp.ui.theme.Blanco
import com.upchiapas.songsapp.ui.theme.BordeSuave
import com.upchiapas.songsapp.ui.theme.Superficie
import java.time.format.DateTimeFormatter
import java.util.Locale

private val formatoFecha = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.forLanguageTag("es-MX"))

@Composable
fun DetallePage(
    onRegresar: () -> Unit,
    onEditar: (Int) -> Unit,
    onEliminada: () -> Unit,
    onSesionVencida: () -> Unit,
    mensaje: String?,
    onMensajeMostrado: () -> Unit,
    viewModel: DetalleViewModel = viewModel(factory = DetalleViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val avisos = remember { SnackbarHostState() }
    MostrarAviso(mensaje = mensaje, estado = avisos, onMostrado = onMensajeMostrado)

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.cargar()
    }

    LaunchedEffect(uiState.eliminada) {
        if (uiState.eliminada) onEliminada()
    }

    LaunchedEffect(uiState.sesionVencida) {
        if (uiState.sesionVencida) onSesionVencida()
    }

    val cancion = uiState.cancion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            BarraAzul(espacioAbajo = 62.dp) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BotonCuadro(icono = Icons.AutoMirrored.Rounded.ArrowBack, descripcion = "Regresar", onClick = onRegresar)
                    Spacer(modifier = Modifier.weight(1f))
                    if (cancion != null) EtiquetaEstado(cancion)
                }
                if (cancion != null) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Encabezado(cancion)
                } else {
                    Spacer(modifier = Modifier.height(76.dp))
                }
            }

            when {
                cancion != null -> Contenido(cancion)

                uiState.error != null -> TarjetaEncimada {
                    MensajeLista(
                        icono = Icons.Outlined.ErrorOutline,
                        colorIcono = MaterialTheme.colorScheme.error,
                        fondoIcono = MaterialTheme.colorScheme.errorContainer,
                        titulo = "No se pudo abrir la canción",
                        mensaje = uiState.error.orEmpty()
                    ) {
                        BotonContorno(texto = "Reintentar", icono = Icons.Rounded.Refresh, onClick = viewModel::cargar)
                    }
                }

                else -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (cancion != null) {
            HorizontalDivider(color = BordeSuave)
            uiState.errorAlEliminar?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
                )
            }
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotonPrincipal(
                    texto = "Editar canción",
                    onClick = { onEditar(cancion.id) },
                    icono = Icons.Outlined.Edit,
                    modifier = Modifier.weight(1f)
                )
                OutlinedIconButton(
                    onClick = viewModel::pedirEliminar,
                    enabled = !uiState.eliminando,
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.6.dp, MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.size(width = 56.dp, height = 52.dp)
                ) {
                    if (uiState.eliminando) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.error)
                    } else {
                        Icon(Icons.Outlined.Delete, contentDescription = "Eliminar canción", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AvisoHost(
            estado = avisos,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 84.dp)
        )
    }

    if (uiState.mostrarConfirmacion && cancion != null) {
        ConfirmarEliminar(
            titulo = cancion.datos.titulo,
            onConfirmar = viewModel::eliminar,
            onCancelar = viewModel::cancelarEliminar
        )
    }
}

@Composable
private fun EtiquetaEstado(cancion: Cancion) {
    val estado = cancion.datos.estado
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(estado.colorFondo())
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconoEstado(estado = estado, tamano = 18.dp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = estado.nombre, style = MaterialTheme.typography.labelMedium, color = estado.colorTexto())
    }
}

@Composable
private fun Encabezado(cancion: Cancion) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Blanco),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = cancion.datos.tonalidad,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(text = cancion.datos.titulo, style = MaterialTheme.typography.headlineMedium, color = Blanco)
            Text(text = cancion.datos.artista, style = MaterialTheme.typography.bodyMedium, color = AzulTexto)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Contenido(cancion: Cancion) {
    val datos = cancion.datos
    TarjetaEncimada {
        Row {
            DatoDetalle(Icons.Outlined.Tag, "Tonalidad", datos.tonalidad, Modifier.weight(1f))
            DatoDetalle(IconoMetronomo, "Tempo", "${datos.bpm} BPM", Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            DatoDetalle(Icons.Outlined.Schedule, "Duración", "${formatearDuracion(datos.duracionMin)} min", Modifier.weight(1f))
            DatoDetalle(Icons.Outlined.CalendarToday, "Último ensayo", datos.fechaEnsayo.format(formatoFecha), Modifier.weight(1f))
        }
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
        Text(text = "Acordes", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            datos.acordes.forEach { acorde ->
                Text(
                    text = acorde,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Superficie)
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }

        if (!datos.letra.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Letra", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = datos.letra,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Superficie)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun DatoDetalle(icono: ImageVector, etiqueta: String, valor: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AzulSuave),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = etiqueta, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = valor, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun ConfirmarEliminar(titulo: String, onConfirmar: () -> Unit, onCancelar: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(24.dp),
        icon = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
            }
        },
        title = { Text(text = "¿Eliminar canción?", style = MaterialTheme.typography.titleLarge) },
        text = {
            Text(
                text = "Se eliminará “$titulo” de tu repertorio. Esta acción no se puede deshacer.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmar,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Eliminar", style = MaterialTheme.typography.labelLarge)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancelar, shape = RoundedCornerShape(12.dp)) {
                Text(text = "Cancelar", style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}
