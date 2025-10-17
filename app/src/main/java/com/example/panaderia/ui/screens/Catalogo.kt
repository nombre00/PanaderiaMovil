package com.example.panaderia.ui.screens

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
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.leerCatalogoLS
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaCatalogo
import com.example.panaderia.ui.components.MenuProductos
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.CatalogoViewModel


@Composable
fun Catalogo(viewModel: CatalogoViewModel = viewModel()){
    panaderia(){
        CatalogoScaffold(viewModel)
    }
}

// Funcion que crea el scaffold.
@Composable
fun CatalogoScaffold(viewModel: CatalogoViewModel){

    // Variable para acceder al contexto
    val contexto = LocalContext.current

    // Creamos una lista de productos que llama los datos del local storage.
    val listaProductos by viewModel.productosFiltrados.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.cargarCatalogo(contexto)
        viewModel.cargarCarritos(contexto)
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

                    // Lista. (Puede que falte algo, un cuadro transparente para separar la lista del fondo).
                    ListaCatalogo(listaProductos, viewModel)
                }
            }

        }
    )
}