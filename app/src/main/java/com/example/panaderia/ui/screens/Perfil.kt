package com.example.panaderia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.panaderia.model.Cliente // <-- Importa tu modelo Cliente
import com.example.panaderia.ui.components.SelectorFotoPerfil
import com.example.panaderia.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(controladorNav: NavHostController) {
    val viewModel: CarritoViewModel = viewModel()
    val contexto = LocalContext.current
    val clienteIngresado by viewModel.clienteIngresado.collectAsStateWithLifecycle()
    val listaEnvios by viewModel.envio.collectAsStateWithLifecycle()

    val navBackStackEntry by controladorNav.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        viewModel.cargarClienteIngresado(contexto)
        viewModel.cargarEnvios(contexto)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectorFotoPerfil()

            Spacer(modifier = Modifier.height(24.dp))

            if (clienteIngresado.id.isNotBlank() && clienteIngresado.id != "0") {
                Text(
                    text = clienteIngresado.nombre,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = clienteIngresado.mail,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Historial de Compras",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            val enviosDelCliente = if (clienteIngresado.id.isNotBlank() && clienteIngresado.id != "0") {
                listaEnvios.filter { it.idCliente == clienteIngresado.id }
            } else {
                emptyList()
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (enviosDelCliente.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Aún no has realizado ninguna compra.")
                        }
                    }
                } else {
                    items(enviosDelCliente) { envio ->
                        val precioTotalEnvio = envio.productos.sumOf { it.precio }
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    text = "Resumen de Compra",
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Total: $${precioTotalEnvio.toInt()}")
                                Text("ID de Seguimiento: ${envio.id}")
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    // llama a la función para limpiar los datos de la sesión.
                    viewModel.cerrarSesion(contexto)

                    //navega a la pantalla de Login y limpia el historial.
                    controladorNav.navigate("Login") {
                        popUpTo(controladorNav.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}
