package com.example.panaderia.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.panaderia.ui.components.FormularioRegistro
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.RegistrarseViewModel


@Composable
fun Registrarse(viewModel: RegistrarseViewModel = RegistrarseViewModel(), controladorNav: NavHostController){

    panaderia(){
        RegistrarseScaffold(controladorNav, viewModel)
    }
}

@Composable
fun RegistrarseScaffold(controladorNav: NavHostController, viewModel: RegistrarseViewModel){
    FormularioRegistro(controladorNav, viewModel)
}