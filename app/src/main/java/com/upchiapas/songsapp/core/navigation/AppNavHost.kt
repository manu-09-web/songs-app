package com.upchiapas.songsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.upchiapas.songsapp.auth.presentation.screens.LoginPage
import com.upchiapas.songsapp.auth.presentation.screens.RegistroPage
import com.upchiapas.songsapp.canciones.presentation.screens.RepertorioPage

@Composable
fun AppNavHost(haySesion: Boolean) {
    val navController = rememberNavController()

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
        composable<RepertorioRuta> {
            RepertorioPage(
                onSesionCerrada = {
                    navController.navigate(LoginRuta()) {
                        popUpTo<RepertorioRuta> { inclusive = true }
                    }
                },
                onSesionVencida = {
                    navController.navigate(LoginRuta(sesionVencida = true)) {
                        popUpTo<RepertorioRuta> { inclusive = true }
                    }
                },
                onAbrirCancion = { },
                onAgregar = { }
            )
        }
    }
}
