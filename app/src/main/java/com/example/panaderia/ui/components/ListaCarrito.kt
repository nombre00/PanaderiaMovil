package com.example.panaderia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Producto
import com.example.panaderia.ui.theme.LilaPastel
import com.example.panaderia.ui.theme.Purple40
import com.example.panaderia.viewmodel.CarritoViewModel


@Composable
fun ListaCarrito( productos: List<Producto>, viewModel: CarritoViewModel = viewModel()){

    // Como la lista de carrito va a contener botones y los botones requieren del contexto de la pagina, vamos a crear una variable que reciba el contexto.
    val contexto = LocalContext.current.applicationContext

    // Contenedor exterior para centrar.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        // Contenedor interior para ordenar verticalmente.
        Column(
            modifier = Modifier
                .background(LilaPastel)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Para hacer scroll.
            horizontalAlignment = Alignment.Start
        ) {
            // Acá van a ir elementos arriba de la lista, como el boton comprar u otras cosas.



            // Por cada producto dentro del carrito tenemos una fila con los datos del producto.
            for (p in productos){
                Column{
                    // Imagen.
                    Image(
                        painter = rememberAsyncImagePainter(p.url), // Referenciamos el url del producto actual.
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 300.dp, height = 240.dp) // Seteamos el tamaño.
                            .padding(6.dp)
                            .background(color = Color.White),
                        contentScale = ContentScale.Crop
                    )
                    // Los textos van dentro de una columna para aprovechar mejor el espacio.
                    Column(){
                        Text(
                            text = p.nombre, // Referenciamos el nombre del producto actual.
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "$"+p.precio.toString(), // Referenciamos el precio del producto actual.
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    // Tambien van botones, los botones deben ir dentro de un contenedor para manejar su tamaño.
                    // Boton aumentar cantidad del producto en carrito.
                    Column(
                        modifier = Modifier
                            .size(width = 200.dp, height = 40.dp)
                    ){
                        Button(
                            // El escuchador
                            onClick = {},
                            shape = RoundedCornerShape(size = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),

                        ) {
                            // Texto dentro del boton.
                            Text(text = "sumar 1 a la cantidad")
                        }
                    }
                    // Boton eliminar del carrito.
                    Column(
                        modifier = Modifier
                            .size(width = 200.dp, height = 40.dp)
                    ){
                        Button(
                            // El escuchador
                            onClick = {},
                            shape = RoundedCornerShape(size = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),

                            ) {
                            // Texto dentro del boton.
                            Text(text = "Eliminar producto")
                        }
                    }
                }
            }

            // Bajo la lista va un boton comprar.

        }
    }
}