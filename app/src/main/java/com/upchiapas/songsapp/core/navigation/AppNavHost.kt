package com.upchiapas.songsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.upchiapas.songsapp.auth.presentation.screens.LoginPage
import com.upchiapas.songsapp.auth.presentation.screens.RegistroPage
import com.upchiapas.songsapp.canciones.presentation.screens.DetallePage
import com.upchiapas.songsapp.canciones.presentation.screens.FormularioPage
import com.upchiapas.songsapp.canciones.presentation.screens.RepertorioPage

private const val MENSAJE = "mensaje"

@Composable
fun AppNavHost(haySesion: Boolean) {
    val navController = rememberNavController()

    val irALoginPorSesionVencida = {
        navController.navigate(LoginRuta(sesionVencida = true)) {
            popUpTo<RepertorioRuta> { inclusive = true }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (haySesion) RepertorioRuta else LoginRuta()
    ) {
        composable<LoginRuta> { entrada ->
            val ruta = entrada.toRoute<LoginRuta>()
            LoginPage(
                aviso = if (ruta.sesionVencida) "Tu sesión expiró, inicia sesión de nuevo" else null,
                onSesionIniciada = {
                    navController.navigate(RepertorioRuta) {
                        popUpTo<LoginRuta> { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(RegistroRuta) }
            )
        }
        composable<RegistroRuta> {
            RegistroPage(
                onRegistrado = {
                    navController.navigate(RepertorioRuta) {
                        popUpTo<LoginRuta> { inclusive = true }
                    }
                },
                onRegresar = { navController.popBackStack() }
            )
        }
        composable<RepertorioRuta> { entrada ->
            val mensaje by entrada.mensaje()
            RepertorioPage(
                onSesionCerrada = {
                    navController.navigate(LoginRuta()) {
                        popUpTo<RepertorioRuta> { inclusive = true }
                    }
                },
                onSesionVencida = irALoginPorSesionVencida,
                onAbrirCancion = { id -> navController.navigate(DetalleRuta(id)) },
                onAgregar = { navController.navigate(FormularioRuta()) },
                mensaje = mensaje,
                onMensajeMostrado = { entrada.savedStateHandle[MENSAJE] = null }
            )
        }
        composable<DetalleRuta> { entrada ->
            val mensaje by entrada.mensaje()
            DetallePage(
                onRegresar = { navController.popBackStack() },
                onEditar = { id -> navController.navigate(FormularioRuta(id)) },
                onEliminada = { navController.regresarConMensaje("Canción eliminada") },
                onSesionVencida = irALoginPorSesionVencida,
                mensaje = mensaje,
                onMensajeMostrado = { entrada.savedStateHandle[MENSAJE] = null }
            )
        }
        composable<FormularioRuta> {
            FormularioPage(
                onCerrar = { navController.popBackStack() },
                onGuardada = { navController.regresarConMensaje("Canción guardada") },
                onSesionVencida = irALoginPorSesionVencida
            )
        }
    }
}

@Composable
private fun NavBackStackEntry.mensaje() =
    savedStateHandle.getStateFlow<String?>(MENSAJE, null).collectAsStateWithLifecycle()

// Deja el mensaje en la pantalla anterior para que lo muestre al regresar.
private fun NavController.regresarConMensaje(mensaje: String) {
    previousBackStackEntry?.savedStateHandle?.set(MENSAJE, mensaje)
    popBackStack()
}
