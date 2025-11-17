package com.example.panaderia.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panaderia.model.Carrito
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaResumenCarrito
import com.example.panaderia.ui.components.ListaResumenEnvios
import com.example.panaderia.ui.components.Mapa
import com.example.panaderia.ui.components.SelectorFotoPerfil
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.PerfilViewModel


@Composable
fun Perfil(viewModel: PerfilViewModel = PerfilViewModel(), controladorNav: NavHostController){
    panaderia(){
        PerfilScaffold(viewModel, controladorNav)
    }
}

// Funcion que crea el scaffold.
@Composable
fun PerfilScaffold(viewModel: PerfilViewModel, controladorNav: NavHostController){

    // Variable para acceder al contexto.
    val contexto = LocalContext.current

    // Escuchamos un estado para llamar al mapa.
    var mostrarMapa by remember { mutableStateOf(false) }


    // Funcionalidad de los carritos.
    // Cargamos todos los carritos
    val listaCarritos by viewModel.carrito.collectAsStateWithLifecycle()
    // Cargamos todos los envios.
    val listaEnvios by viewModel.envio.collectAsStateWithLifecycle()
    // Cargamos el cliente ingresado
    val clienteIngresado by viewModel.clienteIngresado.collectAsStateWithLifecycle()
    // Revisamos el estado del carrito de la base de datos
    LaunchedEffect(Unit) {
        viewModel.cargarCarritos(contexto)
        viewModel.cargarClienteIngresado(contexto)
        viewModel.cargarEnvios(contexto)
    }
    // Pasamos los datos del cliente ingresado
    // Filtramos el carrito del usuario
    val carritoCliente = clienteIngresado.carrito ?: Carrito(0,0,mutableListOf())
    val idCarrito = carritoCliente.id
    val carrito = viewModel.filtrarCarrito(listaCarritos, idCarrito)
    // Filtramos los envios del usuario.
    val envios = viewModel.filtrarEnvios(listaEnvios, clienteIngresado.id)


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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // contenido que va encima de la imagen.
                    Titulo(titulo = "Perfil")

                    SelectorFotoPerfil(viewModel, contexto)

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color.White)
                        .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        if ( clienteIngresado.id != 0) {
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
                    }


                    Spacer(modifier = Modifier.height(40.dp))
                    ListaResumenCarrito(carrito, "Carrito")

                    Spacer(modifier = Modifier.height(40.dp))
                    ListaResumenEnvios(envios, "Envios")


                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = { mostrarMapa = true }) {
                        Text("Mostrar Mapa")
                    }
                    if (mostrarMapa) {
                        Mapa(
                            onDismiss = { mostrarMapa = false }  // Cierra el diálogo
                        )
                    }


                    // Empuja todo lo que viene después hacia abajo
                    Spacer(modifier = Modifier.weight(1f))
                    // Creamos un boton para cerrar sesion.
                    Button(
                        // Dejamos el cliente ingresado como nulo en local storage
                        onClick = {
                            viewModel.cerrarSesion(contexto)
                            controladorNav.navigate("Inicio")
                            Toast.makeText(contexto, "Sesion cerrada", Toast.LENGTH_SHORT).show()
                        }

                    ) { Text("Cerrar sesion") }
                }
            }
        }
    )
}