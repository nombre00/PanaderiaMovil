package com.example.panaderia.ui.screens


import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.leerCatalogoLS
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaCatalogo
import com.example.panaderia.ui.components.MenuProductos
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.CatalogoViewModel


@Composable
fun Catalogo(viewModel: CatalogoViewModel = viewModel(), controladorNav: NavHostController){
    panaderia(){
        CatalogoScaffold(viewModel, controladorNav)
    }
}

// Funcion que crea el scaffold.
@Composable
fun CatalogoScaffold(viewModel: CatalogoViewModel, controladorNav: NavHostController){

    // Variable para acceder al contexto
    val contexto = LocalContext.current

    // Creamos una lista de productos que llama los datos del local storage.
    val listaProductos by viewModel.productosFiltrados.collectAsStateWithLifecycle()
    // Creamos una variable que recibe el cliente ingresado.
    val clienteIngresado by viewModel.clienteIngresado.collectAsStateWithLifecycle()
    // Escuchamos a los carritos.
    val carritos by viewModel.carrito.collectAsStateWithLifecycle()
    // Revisamos el estado del producto
    val productoDetalle by viewModel.detalleProducto.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        // Log.d("LAUNCH", "LaunchedEffect ejecutado!")
        viewModel.cargarCatalogo(contexto)
        viewModel.cargarCarritos(contexto)
        viewModel.cargarClienteIngresado(contexto)
        viewModel.cargarDetalleProducto(contexto)
    }



    Scaffold(
        // Caja que contiene la imagen de fondo.
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
            {
                // Imagen de fondo.
                ImagenFondo(modifier = Modifier.padding(paddingValues))

                Column{
                    // Título de la pagina
                    Titulo(titulo = "Catálogo")

                    // Botones
                    MenuProductos(viewModel)

                    // Lista animada.
                    AnimatedContent(
                        // Este es el estado que escucha, vamos a pasarle la lista de productos que muestra ListaCatalogo()
                        // Es el contenido que va a ir cambiando y lo que va a escuchar la animacion para gatillarse
                        targetState = listaProductos, // Es el gatillador.
                        transitionSpec = { // Parametros de la transision cuando se recarga la lista.
                            // Combinar entrada y salida usando togetherWith
                            (fadeIn(animationSpec = tween(600)) + scaleIn(initialScale = 0.8f)) togetherWith
                                    (fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f))
                        }
                    ) { productos -> // Cuando la lista de productos cambia, se gatilla la animacion del contenido listado abajo, en estricto rigor define que se renderiza.
                        ListaCatalogo(productos, viewModel, clienteIngresado, controladorNav)
                    }

                }
            }

        }
    )
}