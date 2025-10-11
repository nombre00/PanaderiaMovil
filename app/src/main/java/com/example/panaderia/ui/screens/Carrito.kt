package com.example.panaderia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.panaderia.model.Producto
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaCarrito
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.CarritoViewModel


@Composable
fun Carrito(viewModel: CarritoViewModel = viewModel()){

    panaderia(){
        CarritoScaffold()
    }
}

// Funcion que crea el scaffold.
@Composable
fun CarritoScaffold(){

    // Por ahora creamos una lista de obejtos acá, despues vamos a referenciar local Storage.
    val productos = mutableListOf<Producto>()
    // Creamos y agregamos los productos a la lista.
    productos.add(Producto("kuchen_M", "Kuchen de manzana", 19000, "https://cdn0.recetasgratis.net/es/posts/2/9/2/kuchen_de_manzana_facil_y_rapido_45292_orig.jpg"))
    productos.add(Producto("mil_hoja", "Torta de Milhoja", 16000, "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg"))


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
                    ListaCarrito(productos = productos)
                }
            }
        }
    )
}