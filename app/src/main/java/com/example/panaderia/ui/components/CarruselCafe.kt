package com.example.panaderia.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.panaderia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarruselCafe() {
    // Creamos un estado para que lo recuerde.
    val estadoCarrusel = rememberCarouselState { 4 }

    // Cargamos el contexto.
    val contexto = LocalContext.current

    HorizontalMultiBrowseCarousel(
        state = estadoCarrusel,
        preferredItemWidth = 340.dp,
        itemSpacing = 10.dp
    ) { pagina ->

        Box(modifier = Modifier
            .size(340.dp)
            .clickable{
                Toast.makeText(contexto, "funcionalidad pendiente", Toast.LENGTH_SHORT).show()
            }
        ){
            Image(
                painter = painterResource(
                    id = when (pagina){
                        0 -> R.drawable.cafelate
                        1 -> R.drawable.cafeamericano
                        2 -> R.drawable.cafemocachino
                        else -> R.drawable.cafehelado
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}