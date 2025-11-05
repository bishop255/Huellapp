package com.example.huellapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.huellapp.model.Paseador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaseadoresScreen(listaPerros: List<String>) {
    val paseadores = remember {
        listOf(
            Paseador("1", "Carlos Rodríguez", 4.8f, 3, 15000, true),
            Paseador("2", "Danae Guerrero", 4.9f, 5, 18000, true),
            Paseador("3", "Juan Pérez", 4.5f, 2, 12000, false),
            Paseador("4", "Jhotzean Oquendo", 4.2f, 4, 16000, true),
            Paseador("5", "Ambar Soto", 4.0f, 1, 12000, false)
        )
    }

    var mostrarSeleccionPerro by remember { mutableStateOf(false) }
    var perroSeleccionado by remember { mutableStateOf<String?>(null) }
    var paseoEnCurso by remember { mutableStateOf(false) }
    var paseoFinalizado by remember { mutableStateOf(false) }
    var paseadorSeleccionado by remember { mutableStateOf<Paseador?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paseadores Disponibles") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(paseadores) { paseador ->
                PaseadorCard(
                    paseador,
                    onSolicitar = {
                        paseadorSeleccionado = paseador
                        perroSeleccionado = null
                        mostrarSeleccionPerro = true
                    },
                    paseoEnCurso = (paseoEnCurso && paseadorSeleccionado == paseador),
                    paseoFinalizado = (paseoFinalizado && paseadorSeleccionado == paseador)
                )
            }
        }
    }

    if (mostrarSeleccionPerro) {
        AlertDialog(
            onDismissRequest = { mostrarSeleccionPerro = false },
            title = { Text("Selecciona un perro para el paseo") },
            text = {
                if (listaPerros.isEmpty()) {
                    Text("Perro no disponible")
                } else {
                    Column {
                        listaPerros.forEach { perro ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                RadioButton(
                                    selected = perro == perroSeleccionado,
                                    onClick = { perroSeleccionado = perro },
                                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(perro)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (perroSeleccionado != null && paseadorSeleccionado != null) {
                            mostrarSeleccionPerro = false
                            paseoEnCurso = true
                            paseoFinalizado = false
                        }
                    },
                    enabled = listaPerros.isNotEmpty() && perroSeleccionado != null
                ) {
                    Text("Iniciar Paseo")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarSeleccionPerro = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (paseoEnCurso && paseadorSeleccionado != null && perroSeleccionado != null) {
        LaunchedEffect(paseadorSeleccionado?.id + perroSeleccionado) {
            delay(5000)
            paseoEnCurso = false
            paseoFinalizado = true
        }
    }
}

@Composable
fun PaseadorCard(
    paseador: Paseador,
    onSolicitar: () -> Unit,
    paseoEnCurso: Boolean,
    paseoFinalizado: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(56.dp).clip(CircleShape),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.Person, contentDescription = null, modifier = Modifier.size(28.dp))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = paseador.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${paseador.calificacion} • ${paseador.experiencia} años exp.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = "$${paseador.tarifa}/paseo",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (paseador.disponible) {
                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Disponible",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onSolicitar,
                modifier = Modifier.fillMaxWidth(),
                enabled = paseador.disponible && !paseoEnCurso
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Solicitar Paseo")
            }
            if (paseoEnCurso) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Paseo en curso...", style = MaterialTheme.typography.bodyMedium)
            }
            if (paseoFinalizado) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("¡Paseo finalizado!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
