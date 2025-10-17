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
fun GestionCarrito(viewModel: CarritoViewModel, idCarrito: String = "1"){

    panaderia(){
        CarritoScaffold(viewModel)
    }
}

// Funcion que crea el scaffold, tiene que ser reactiva.
@Composable
fun CarritoScaffold(viewModel: CarritoViewModel){

    // Variable para acceder al contexto
    val contexto = LocalContext.current

    // Observamos el estado del carrito desde el viewModel
    val carrito by viewModel.carrito.collectAsStateWithLifecycle()
    val carritoId = "1" // ID hardcodeado por ahora, luego tomamos esto del usuario ingresado en el contexto.

    // Cargar el carrito al iniciar
    LaunchedEffect(carritoId) {
        // Revisamos que el carrito no se cargue más de una vez
        if (viewModel.carrito.value  == null){
            viewModel.cargarCarrito(contexto, carritoId) // LLamamos a la funcion de CarritoViewModel
        }
    }

    // Preguntar si esto va an ViewModel
    // Calcular el precio total
    val precio = carrito?.productos?.sumOf { it.precio } ?: 0 // Funcion lambda de kotlin, si no encontramos datos retorna 0.


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

                    // Lista de productos en el carrito.
                    carrito?.let { // Si el carrito no está vacio va a retornar un carrito que es el iterador pasado como parametro a ListaCarrito
                        ListaCarrito(carrito = it, viewModel = viewModel, precio = precio)
                    } ?: run {
                        // Mostrar un mensaje si el carrito es nulo, a mejorar
                        Titulo(titulo = "Carrito vacío")
                    }
                }
            }
        }
    )
}