package com.example.panaderia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.panaderia.model.Carrito
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaCarrito
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.CarritoViewModel
import com.example.panaderia.ui.screens.MapaDialog
import com.example.panaderia.util.NotificacionHelper


@Composable
fun GestionCarrito(viewModel: CarritoViewModel, idCarrito: String = "1"){
    panaderia {
        CarritoScaffold(viewModel)
    }
}

@Composable
fun CarritoScaffold(viewModel: CarritoViewModel){
    var mostrarDialogoMapa by remember { mutableStateOf(false) }
    val contexto = LocalContext.current

    // Estados que escucha la pagina (esto ya estaba bien)
    val listaCarritos by viewModel.carritos.collectAsStateWithLifecycle()
    val clienteIngresado by viewModel.clienteIngresado.collectAsStateWithLifecycle()
    val listaEnvios by viewModel.envio.collectAsStateWithLifecycle()
    val refresco by viewModel.refresco.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.cargarCarritos(contexto)
        viewModel.cargarClienteIngresado(contexto)
        viewModel.cargarEnvios(contexto)
        viewModel.cargarRefresco(contexto)
    }

    val idCliente = clienteIngresado.id
    // filtro del carrito por el id del cliente
    val carrito = listaCarritos.find { it.idCliente == idCliente }
        ?: Carrito("0", "0", mutableListOf()) // Valor por defecto si no se encuentra

    // Calcular el precio total
    val precio = carrito.productos.sumOf { it.precio }

    // El Scaffold ahora organiza toda la pantalla
    Scaffold(
        // En la barra inferior ponemos el total y el botón de compra
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total: $${precio.toInt()}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        // 1- Muestra la notificación
                        NotificacionHelper.mostrarNotificacionDeCompra(contexto)

                        //  2-Abre el diálogo del mapa
                        mostrarDialogoMapa = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar Compra")
                }
            }
        }
    ) { paddingValues ->
        // Contenido principal de la pantalla
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplicamos el padding para que el contenido no se oculte
        ){
            // Imagen de fondo.
            ImagenFondo(modifier = Modifier.fillMaxSize())

            // Contenido que va encima de la imagen.
            Column {
                Titulo(titulo = "Carrito de compra")
                ListaCarrito(carrito = carrito, viewModel = viewModel, precio = precio)
            }
        }
    }

    // Lógica para mostrar el diálogo del mapa
    // Se muestra como una superposición sobre toda la pantalla
    if (mostrarDialogoMapa) {
        MapaDialog(onDismiss = {
            mostrarDialogoMapa = false
            // Opcional: Aquí podrías navegar a otra pantalla o vaciar el carrito
            // viewModel.vaciarCarrito(contexto)
        })
    }
}
