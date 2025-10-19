package com.example.panaderia.ui.screens

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Producto
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.viewmodel.DetalleProductoViewModel


// Al hacerle click a un producto en el catalogo nos manda a esta pagina que contiene más datos del producto.
@Composable
fun DetalleProducto(viewModel: DetalleProductoViewModel, producto: Producto){
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                // Imagen de fondo.
                ImagenProducto(producto)
                Column(modifier = Modifier.height( 600.dp).fillMaxWidth().background(color = Color.Gray)) {
                    // Elementos: nombre del producto, una corta reseña, precio
                    Text(text = producto.nombre, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    // precio.
                    Text(text = "Precio: $" + producto.precio, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    // Receña.
                    Text(text = "reseña", fontSize = 12.sp, fontWeight = FontWeight.Thin)
                }
            }
        }
    )
}

// Componente que contiene la imagen de fondo, que es una imagen del producto.
@Composable
fun ImagenProducto(producto: Producto){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = rememberAsyncImagePainter(producto.url),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop  // Ajusta la imagen al contenedor.
        )
    }
}