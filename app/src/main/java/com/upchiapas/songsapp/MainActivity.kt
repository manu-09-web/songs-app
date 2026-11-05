package com.upchiapas.songsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.songsapp.ui.theme.SongsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SongsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    Bienvenida(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun Bienvenida(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tu banda,", style = MaterialTheme.typography.headlineLarge)
        Text(text = "en orden.", style = MaterialTheme.typography.headlineLarge)
        Text(
            text = "Songs App · repertorio de banda",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BienvenidaPreview() {
    SongsAppTheme { Bienvenida() }
}
