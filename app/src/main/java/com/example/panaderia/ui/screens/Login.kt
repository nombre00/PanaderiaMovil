package com.example.panaderia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.InicioViewModel


@Composable
fun Login(viewModel: InicioViewModel = viewModel()){
    panaderia(){
        LoginScaffold()
    }
}

// Funcion que crea el scaffold.
@Composable
fun LoginScaffold(){
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
                Titulo(titulo = "Login")
            }
        }
    )
}