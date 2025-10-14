package com.example.panaderia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


// Esto tiene que ser una card con imagenes que vayan cambiando cada cierto tiempo o al hacerles click.
@Composable
fun ImagenesAnimables(){
    // Variables que contienen cada una una imagen.
    val img1 = "https://www.puffpastry.com/wp-content/uploads/2017/02/BH_7284.jpg"
    val img2 = ""
    val img3 = ""

    // Una caja que contenga las imagenes.
    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(height = 200.dp, width = 300.dp),
        Alignment.Center
    ){
        Image(
            painter = rememberAsyncImagePainter(img1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop  // Ajusta la imagen al contenedor.
        )
    }
}