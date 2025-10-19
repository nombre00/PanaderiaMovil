package com.example.panaderia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaResumenCarrito
import com.example.panaderia.ui.components.ListaResumenEnvios
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.PerfilViewModel


@Composable
fun Perfil(viewModel: PerfilViewModel = viewModel()){
    panaderia(){
        PerfilScaffold(viewModel)
    }
}

// Funcion que crea el scaffold.
@Composable
fun PerfilScaffold(viewModel: PerfilViewModel){

    // Variable para acceder al contexto.
    val contexto = LocalContext.current

    // Por ahora seguimos hardcoreando el id del cliente a 1.


    // Funcionalidad de los carritos.
    // Cargamos todos los carritos
    val listaCarritos by viewModel.carrito.collectAsStateWithLifecycle()
    // Revisamos el estado del carrito de la base de datos
    LaunchedEffect(Unit) {
        viewModel.cargarCarritos(contexto)
    }
    // Filtramos el carrito del usuario
    val carrito = viewModel.filtrarCarrito(listaCarritos, "1")


    // Funcionalidad de los envios.
    // Cargamos todos los envios.
    val listaEnvios by viewModel.envio.collectAsStateWithLifecycle()
    // Revisamos el estado de los envios en la base de datos.
    LaunchedEffect(Unit) {
        viewModel.cargarEnvios(contexto)
    }
    // Filtramos los envios del usuario.
    val envios = viewModel.filtrarEnvios(listaEnvios, "1")


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
                Column {
                    // contenido que va encima de la imagen.
                    Titulo(titulo = "Perfil")
                    Spacer(modifier = Modifier.height(40.dp))
                    ListaResumenCarrito(carrito, "Carrito")

                    Spacer(modifier = Modifier.height(40.dp))
                    ListaResumenEnvios(envios, "Envios")
                }
            }
        }
    )
}