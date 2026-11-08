package com.upchiapas.songsapp.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.songsapp.auth.presentation.viewmodels.LoginViewModel
import com.upchiapas.songsapp.ui.components.BarraAzul
import com.upchiapas.songsapp.ui.components.BotonPrincipal
import com.upchiapas.songsapp.ui.components.CampoTexto
import com.upchiapas.songsapp.ui.components.TarjetaEncimada
import com.upchiapas.songsapp.ui.theme.AzulTexto
import com.upchiapas.songsapp.ui.theme.Blanco

@Composable
fun LoginPage(
    onSesionIniciada: () -> Unit,
    onIrARegistro: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.sesionIniciada) {
        if (uiState.sesionIniciada) onSesionIniciada()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        BarraAzul {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Blanco),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.GraphicEq,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Tu banda,\nen orden.", style = MaterialTheme.typography.headlineLarge, color = Blanco)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Inicia sesión para ver tu repertorio.", style = MaterialTheme.typography.bodyMedium, color = AzulTexto)
        }

        TarjetaEncimada {
            CampoTexto(
                etiqueta = "Correo",
                valor = uiState.email,
                onValorChange = viewModel::onEmailChange,
                icono = Icons.Outlined.Email,
                error = uiState.errorEmail,
                tipoTeclado = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                etiqueta = "Contraseña",
                valor = uiState.password,
                onValorChange = viewModel::onPasswordChange,
                icono = Icons.Outlined.Lock,
                error = uiState.errorPassword,
                tipoTeclado = KeyboardType.Password,
                esPassword = true,
                passwordVisible = uiState.passwordVisible,
                onCambiarVisibilidad = viewModel::onCambiarVisibilidad
            )

            uiState.errorGeneral?.let { mensaje ->
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = mensaje, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            BotonPrincipal(
                texto = "Iniciar sesión",
                onClick = viewModel::iniciarSesion,
                cargando = uiState.cargando,
                icono = Icons.AutoMirrored.Rounded.ArrowForward
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿No tienes cuenta?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onIrARegistro) {
                Text(text = "Crear cuenta", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
