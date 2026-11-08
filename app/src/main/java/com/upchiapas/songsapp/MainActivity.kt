package com.upchiapas.songsapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.upchiapas.songsapp.core.navigation.AppNavHost
import com.upchiapas.songsapp.ui.theme.SongsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        val container = (application as SongsApplication).container
        setContent {
            SongsAppTheme {
                AppNavHost(haySesion = container.authRepository.haySesion())
            }
        }
    }
}
