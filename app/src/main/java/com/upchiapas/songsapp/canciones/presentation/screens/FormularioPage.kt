package com.upchiapas.songsapp.canciones.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.songsapp.canciones.domain.model.EstadoCancion
import com.upchiapas.songsapp.canciones.presentation.components.color
import com.upchiapas.songsapp.canciones.presentation.viewmodels.FormularioViewModel
import com.upchiapas.songsapp.ui.components.BarraAzul
import com.upchiapas.songsapp.ui.components.BotonCuadro
import com.upchiapas.songsapp.ui.components.BotonPrincipal
import com.upchiapas.songsapp.ui.components.CampoTexto
import com.upchiapas.songsapp.ui.components.IconoMetronomo
import com.upchiapas.songsapp.ui.theme.AzulTexto
import com.upchiapas.songsapp.ui.theme.Blanco
import com.upchiapas.songsapp.ui.theme.BordeSuave
import com.upchiapas.songsapp.ui.theme.Superficie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val MILIS_POR_DIA = 86_400_000L
private val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")

@Composable
fun FormularioPage(
    onCerrar: () -> Unit,
    onGuardada: (esEdicion: Boolean) -> Unit,
    onSesionVencida: () -> Unit,
    viewModel: FormularioViewModel = viewModel(factory = FormularioViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.guardada) {
        if (uiState.guardada) onGuardada(uiState.esEdicion)
    }

    LaunchedEffect(uiState.sesionVencida) {
        if (uiState.sesionVencida) onSesionVencida()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .navigationBarsPadding()
    ) {
        BarraAzul(espacioAbajo = 24.dp) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BotonCuadro(icono = Icons.Rounded.Close, descripcion = "Cerrar", onClick = onCerrar)
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = if (uiState.esEdicion) "Editar canción" else "Nueva canción",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Blanco
                    )
                    Text(text = "Llena los datos de la canción.", style = MaterialTheme.typography.bodyMedium, color = AzulTexto)
                }
            }
        }

        if (uiState.cargando) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CampoTexto(
                etiqueta = "Título",
                valor = uiState.titulo,
                onValorChange = viewModel::onTituloChange,
                icono = Icons.Outlined.MusicNote,
                error = uiState.errores["titulo"]
            )
            CampoTexto(
                etiqueta = "Artista",
                valor = uiState.artista,
                onValorChange = viewModel::onArtistaChange,
                icono = Icons.Outlined.Mic,
                error = uiState.errores["artista"]
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CampoTexto(
                    etiqueta = "Tonalidad",
                    valor = uiState.tonalidad,
                    onValorChange = viewModel::onTonalidadChange,
                    icono = Icons.Outlined.Tag,
                    error = uiState.errores["tonalidad"],
                    modifier = Modifier.weight(1f)
                )
                CampoTexto(
                    etiqueta = "Tempo",
                    valor = uiState.bpm,
                    onValorChange = viewModel::onBpmChange,
                    icono = IconoMetronomo,
                    error = uiState.errores["bpm"],
                    tipoTeclado = KeyboardType.Number,
                    sufijo = "BPM",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CampoTexto(
                    etiqueta = "Duración",
                    valor = uiState.duracion,
                    onValorChange = viewModel::onDuracionChange,
                    icono = Icons.Outlined.Schedule,
                    error = uiState.errores["duracionMin"],
                    tipoTeclado = KeyboardType.Decimal,
                    sufijo = "min",
                    modifier = Modifier.weight(1f)
                )
                CampoFecha(
                    fecha = uiState.fechaEnsayo,
                    error = uiState.errores["fechaEnsayo"],
                    onClick = viewModel::abrirCalendario,
                    modifier = Modifier.weight(1f)
                )
            }
            CampoAcordes(
                acordes = uiState.acordes,
                nuevo = uiState.acordeNuevo,
                error = uiState.errores["acordes"],
                onNuevoChange = viewModel::onAcordeNuevoChange,
                onAgregar = { viewModel.agregarAcorde() },
                onQuitar = viewModel::quitarAcorde
            )
            CampoTexto(
                etiqueta = "Letra (opcional)",
                valor = uiState.letra,
                onValorChange = viewModel::onLetraChange,
                icono = Icons.Outlined.Notes,
                error = uiState.errores["letra"],
                ayuda = "${uiState.letra.length} / 5000",
                lineas = 4
            )
            SelectorEstado(seleccionado = uiState.estado, onSeleccionar = viewModel::onEstadoChange)

            uiState.errorGeneral?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
            }
        }

        HorizontalDivider(color = BordeSuave)
        BotonPrincipal(
            texto = "Guardar canción",
            onClick = viewModel::guardar,
            cargando = uiState.guardando,
            icono = Icons.Outlined.Save,
            modifier = Modifier.padding(16.dp)
        )
    }

    if (uiState.mostrarCalendario) {
        Calendario(
            fecha = uiState.fechaEnsayo,
            onElegir = viewModel::onFechaChange,
            onCerrar = viewModel::cerrarCalendario
        )
    }
}

@Composable
private fun CampoFecha(fecha: LocalDate?, error: String?, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colorBorde = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    Column(modifier = modifier) {
        Text(
            text = "Último ensayo",
            style = MaterialTheme.typography.labelMedium,
            color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, colorBorde, RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = fecha?.format(formatoFecha) ?: "Elegir",
                style = MaterialTheme.typography.bodyLarge,
                color = if (fecha == null) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
        }
        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 4.dp, top = 6.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CampoAcordes(
    acordes: List<String>,
    nuevo: String,
    error: String?,
    onNuevoChange: (String) -> Unit,
    onAgregar: () -> Unit,
    onQuitar: (Int) -> Unit
) {
    Column {
        Text(
            text = "Acordes",
            style = MaterialTheme.typography.labelMedium,
            color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (acordes.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                acordes.forEachIndexed { i, acorde ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Superficie)
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                            .clickable { onQuitar(i) }
                            .padding(start = 12.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = acorde, style = MaterialTheme.typography.labelMedium)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = "Quitar $acorde",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        OutlinedTextField(
            value = nuevo,
            onValueChange = onNuevoChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = error != null,
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = { Text(text = "Agregar acorde…", style = MaterialTheme.typography.bodyLarge) },
            trailingIcon = {
                IconButton(onClick = onAgregar) {
                    Icon(Icons.Rounded.Add, contentDescription = "Agregar acorde")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onAgregar() }),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = MaterialTheme.colorScheme.outline)
        )
        Text(
            text = error ?: "Escribe un acorde y presiona Enter o espacio. Ej.: Am, G7, Cmaj7. Toca uno para quitarlo.",
            style = MaterialTheme.typography.labelSmall,
            color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp, top = 6.dp)
        )
    }
}

@Composable
private fun SelectorEstado(seleccionado: EstadoCancion, onSeleccionar: (EstadoCancion) -> Unit) {
    Column {
        Text(text = "Estado", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Superficie)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            EstadoCancion.entries.forEach { estado ->
                val activo = estado == seleccionado
                Text(
                    text = estado.nombre,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (activo) Blanco else MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(9.dp))
                        .background(if (activo) estado.color() else Superficie)
                        .clickable { onSeleccionar(estado) }
                        .padding(vertical = 10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Calendario(fecha: LocalDate?, onElegir: (LocalDate) -> Unit, onCerrar: () -> Unit) {
    val hoy = LocalDate.now().toEpochDay() * MILIS_POR_DIA
    val estado = rememberDatePickerState(
        initialSelectedDateMillis = (fecha ?: LocalDate.now()).toEpochDay() * MILIS_POR_DIA,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long) = utcTimeMillis <= hoy
        }
    )
    DatePickerDialog(
        onDismissRequest = onCerrar,
        confirmButton = {
            TextButton(onClick = {
                estado.selectedDateMillis?.let { onElegir(LocalDate.ofEpochDay(it / MILIS_POR_DIA)) }
            }) { Text("Aceptar") }
        },
        dismissButton = { TextButton(onClick = onCerrar) { Text("Cancelar") } }
    ) {
        DatePicker(state = estado)
    }
}
