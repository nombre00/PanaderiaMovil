package com.example.panaderia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


// Funcion constructora de la imagen de fondo.
@Composable
fun ImagenFondo(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    content: @Composable () -> Unit = {}  // Contenido que va encima de la imagen.
    ){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = rememberAsyncImagePainter("https://previews.123rf.com/images/saenal78/saenal781601/saenal78160100012/50848547-seamless-pattern-with-bread-and-wheat-hand-drawn-bakery-background-vector-illustration.jpg"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop  // Ajusta la imagen al contenedor.
        )
        // Contenido proporcionado por el llamador.
        content()
    }
}