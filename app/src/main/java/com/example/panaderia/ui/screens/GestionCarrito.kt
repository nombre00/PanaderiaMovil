package com.example.panaderia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.model.Carrito
import com.example.panaderia.repository.leerCarritos
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaCarrito
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.CarritoViewModel
import com.example.panaderia.model.Producto


@Composable
fun GestionCarrito(viewModel: CarritoViewModel = CarritoViewModel()){

    panaderia(){
        CarritoScaffold(viewModel)
    }
}

// Funcion que crea el scaffold, tiene que ser reactiva.
@Composable
fun CarritoScaffold(viewModel: CarritoViewModel){

    // Variable para acceder al contexto
    val contexto = LocalContext.current
    // Estados que escucha la pagina
    val listaCarritos by viewModel.carritos.collectAsStateWithLifecycle()
    val clienteIngresado by viewModel.clienteIngresado.collectAsStateWithLifecycle()

    val listaEnvios by viewModel.envio.collectAsStateWithLifecycle()
    // Trampa
    val refresco by viewModel.refresco.collectAsStateWithLifecycle()

    // Corrutinas que revisa los estados
    LaunchedEffect(Unit) {
        viewModel.cargarCarritos(contexto)
        viewModel.cargarClienteIngresado(contexto)
        viewModel.cargarEnvios(contexto)
        viewModel.cargarRefresco(contexto)
    }

    // Usamos el id del cliente ingresado para buscar su carrito.
    val idCliente = clienteIngresado.id
    // Filtramos el carrito reactivamente.
    val carrito = listaCarritos.find { it.idCliente == idCliente }
         ?: Carrito("0", "0", mutableListOf()) // ponemos esto para inicializar el valor si o si

    // Calcular el precio total
    val precio = carrito.productos.sumOf { it.precio }




    Scaffold(
        // Caja que contiene la imagen de fondo.
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                // Imagen de fondo.
                ImagenFondo(
                    modifier = Modifier.padding(paddingValues)
                )
                Column(){
                    // contenido que va encima de la imagen.
                    Titulo(titulo = "Carrito de compra")


                    ListaCarrito(carrito = carrito, viewModel = viewModel, precio = precio)

                }
            }
        }
    )
}