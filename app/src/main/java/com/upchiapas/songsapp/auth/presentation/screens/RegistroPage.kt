package com.upchiapas.songsapp.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.songsapp.auth.presentation.viewmodels.RegistroViewModel
import com.upchiapas.songsapp.ui.components.BarraAzul
import com.upchiapas.songsapp.ui.components.BotonCuadro
import com.upchiapas.songsapp.ui.components.BotonPrincipal
import com.upchiapas.songsapp.ui.components.CampoTexto
import com.upchiapas.songsapp.ui.components.TarjetaEncimada
import com.upchiapas.songsapp.ui.theme.AzulTexto
import com.upchiapas.songsapp.ui.theme.Blanco

@Composable
fun RegistroPage(
    onRegistrado: () -> Unit,
    onRegresar: () -> Unit,
    viewModel: RegistroViewModel = viewModel(factory = RegistroViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.registrado) {
        if (uiState.registrado) onRegistrado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        BarraAzul(espacioAbajo = 60.dp) {
            BotonCuadro(icono = Icons.AutoMirrored.Rounded.ArrowBack, descripcion = "Regresar", onClick = onRegresar)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Crear cuenta", style = MaterialTheme.typography.headlineLarge, color = Blanco)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Guarda el repertorio de tu banda.", style = MaterialTheme.typography.bodyMedium, color = AzulTexto)
        }

        TarjetaEncimada {
            CampoTexto(
                etiqueta = "Nombre",
                valor = uiState.nombre,
                onValorChange = viewModel::onNombreChange,
                icono = Icons.Outlined.Person,
                error = uiState.errores["nombre"]
            )
            Spacer(modifier = Modifier.height(14.dp))
            CampoTexto(
                etiqueta = "Correo",
                valor = uiState.email,
                onValorChange = viewModel::onEmailChange,
                icono = Icons.Outlined.Email,
                error = uiState.errores["email"],
                tipoTeclado = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(14.dp))
            CampoTexto(
                etiqueta = "Contraseña",
                valor = uiState.password,
                onValorChange = viewModel::onPasswordChange,
                icono = Icons.Outlined.Lock,
                error = uiState.errores["password"],
                ayuda = "Mínimo 8 caracteres",
                tipoTeclado = KeyboardType.Password,
                esPassword = true,
                passwordVisible = uiState.passwordVisible,
                onCambiarVisibilidad = viewModel::onCambiarVisibilidad
            )
            Spacer(modifier = Modifier.height(14.dp))
            CampoTexto(
                etiqueta = "Confirmar contraseña",
                valor = uiState.confirmacion,
                onValorChange = viewModel::onConfirmacionChange,
                icono = Icons.Outlined.Lock,
                error = uiState.errores["confirmacion"],
                tipoTeclado = KeyboardType.Password,
                esPassword = true,
                passwordVisible = uiState.passwordVisible,
                onCambiarVisibilidad = viewModel::onCambiarVisibilidad
            )

            uiState.errorGeneral?.let { mensaje ->
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = mensaje, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(20.dp))
            BotonPrincipal(
                texto = "Crear cuenta",
                onClick = viewModel::registrar,
                cargando = uiState.cargando
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
                text = "¿Ya tienes cuenta?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onRegresar) {
                Text(text = "Inicia sesión", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
