package com.example.panaderia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.FormularioLogin
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.LoginViewModel


@Composable
fun Login(viewModel: LoginViewModel = viewModel(), controladorNav: NavHostController){
    panaderia(){
        LoginScaffold(controladorNav, viewModel)
    }
}

// Funcion que crea el scaffold.
@Composable
fun LoginScaffold(controladorNav: NavHostController, viewModel: LoginViewModel){
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

                Column(modifier = Modifier.fillMaxSize()){
                    FormularioLogin(controladorNav, viewModel)
                }
            }
        }
    )
}