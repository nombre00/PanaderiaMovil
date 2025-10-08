package com.example.panaderia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.InicioViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.Titulo

@Composable
fun Inicio(viewModel: InicioViewModel = viewModel()) {
    // Estado de la pagina (creo que se usa para comunicarse con otros componentes)
    val mensaje by viewModel.message.collectAsState() // Observa el estado

    panaderia {



       InicioScaffold()
    }
}

// Funcion que crea el scaffold.
@Composable
fun InicioScaffold(){
    Scaffold(
        // Footer.
        bottomBar = { Footer() },
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
                // contenido que va encima de la imagen.
                Titulo(titulo = "Inicio")
            }
        }
    )
}