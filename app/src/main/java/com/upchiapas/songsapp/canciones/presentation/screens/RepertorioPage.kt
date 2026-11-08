package com.upchiapas.songsapp.canciones.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.songsapp.canciones.presentation.viewmodels.RepertorioViewModel
import com.upchiapas.songsapp.ui.components.BarraAzul
import com.upchiapas.songsapp.ui.components.BotonCuadro
import com.upchiapas.songsapp.ui.components.TarjetaEncimada
import com.upchiapas.songsapp.ui.theme.AzulSuave
import com.upchiapas.songsapp.ui.theme.AzulTexto
import com.upchiapas.songsapp.ui.theme.Blanco

@Composable
fun RepertorioPage(
    onSesionCerrada: () -> Unit,
    viewModel: RepertorioViewModel = viewModel(factory = RepertorioViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.sesionCerrada) {
        if (uiState.sesionCerrada) onSesionCerrada()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
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
                    Text(text = uiState.iniciales, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = uiState.saludo, style = MaterialTheme.typography.bodyMedium, color = AzulTexto)
                    Text(text = uiState.nombreUsuario, style = MaterialTheme.typography.titleLarge, color = Blanco)
                }
                BotonCuadro(
                    icono = Icons.AutoMirrored.Outlined.Logout,
                    descripcion = "Cerrar sesión",
                    onClick = viewModel::cerrarSesion
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Tu repertorio", style = MaterialTheme.typography.headlineMedium, color = Blanco)
        }

        TarjetaEncimada {
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
                        .background(AzulSuave),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MusicNote,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Agrega tu primera canción", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Guarda sus acordes, su letra y qué tanto la domina la banda.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
