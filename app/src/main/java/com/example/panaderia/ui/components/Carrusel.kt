package com.example.panaderia.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panaderia.R
import com.example.panaderia.model.Cliente
import com.example.panaderia.viewmodel.InicioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carrusel(viewModel: InicioViewModel, cliente: Cliente, controladorNav: NavHostController) {
    // Creamos un estado para que lo recuerde.
    val estadoCarrusel = rememberCarouselState { 4 }

    // Cargamos el contexto.
    val contexto = LocalContext.current

    HorizontalMultiBrowseCarousel(
        state = estadoCarrusel,
        preferredItemWidth = 340.dp,
        itemSpacing = 16.dp
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
                        0 -> R.drawable.marraquetas
                        1 -> R.drawable.pandehuevo
                        2 -> R.drawable.dobladitas
                        else -> R.drawable.mediaslunas
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .clickable(enabled = true,
                        onClick = {
                            val bandera = viewModel.clickOfertas(cliente, contexto)
                            if (bandera){
                                controladorNav.navigate("Catalogo")
                            } else {
                                // Toast con mensaje de ingrese
                                Toast.makeText(contexto, "Inicie sesión", Toast.LENGTH_SHORT).show()
                            }
                        })
            )
        }
    }
}