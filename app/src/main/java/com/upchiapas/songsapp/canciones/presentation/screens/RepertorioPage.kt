package com.upchiapas.songsapp.canciones.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.songsapp.canciones.presentation.components.FiltrosEstado
import com.upchiapas.songsapp.canciones.presentation.components.ResumenRepertorio
import com.upchiapas.songsapp.canciones.presentation.components.TarjetaCancion
import com.upchiapas.songsapp.canciones.presentation.viewmodels.RepertorioViewModel
import com.upchiapas.songsapp.ui.components.BarraAzul
import com.upchiapas.songsapp.ui.components.BotonCuadro
import com.upchiapas.songsapp.ui.components.TarjetaEncimada
import com.upchiapas.songsapp.ui.theme.AzulTexto
import com.upchiapas.songsapp.ui.theme.Blanco

@Composable
fun RepertorioPage(
    onSesionCerrada: () -> Unit,
    onAbrirCancion: (Int) -> Unit,
    onAgregar: () -> Unit,
    viewModel: RepertorioViewModel = viewModel(factory = RepertorioViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.cargarCanciones()
    }

    LaunchedEffect(uiState.sesionCerrada) {
        if (uiState.sesionCerrada) onSesionCerrada()
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAgregar,
                shape = RoundedCornerShape(18.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Blanco,
                icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
                text = { Text(text = "Agregar", style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            contentPadding = PaddingValues(bottom = 112.dp)
        ) {
            item {
                Encabezado(
                    saludo = uiState.saludo,
                    nombre = uiState.nombreUsuario,
                    iniciales = uiState.iniciales,
                    onCerrarSesion = viewModel::cerrarSesion
                )
            }
            item {
                TarjetaEncimada {
                    ResumenRepertorio(
                        total = uiState.canciones.size,
                        dominadas = uiState.dominadas,
                        aMedias = uiState.aMedias
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                FiltrosEstado(
                    seleccionado = uiState.filtro,
                    total = uiState.canciones.size,
                    onSeleccionar = viewModel::onFiltroChange
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(uiState.cancionesFiltradas, key = { it.id }) { cancion ->
                TarjetaCancion(
                    cancion = cancion,
                    onClick = { onAbrirCancion(cancion.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun Encabezado(
    saludo: String,
    nombre: String,
    iniciales: String,
    onCerrarSesion: () -> Unit
) {
    BarraAzul(espacioAbajo = 62.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Blanco),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iniciales, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = saludo, style = MaterialTheme.typography.bodyMedium, color = AzulTexto)
                Text(text = nombre, style = MaterialTheme.typography.titleLarge, color = Blanco)
            }
            BotonCuadro(
                icono = Icons.AutoMirrored.Outlined.Logout,
                descripcion = "Cerrar sesión",
                onClick = onCerrarSesion
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Tu repertorio", style = MaterialTheme.typography.headlineMedium, color = Blanco)
    }
}
