package com.example.panaderia.ui.components

import android.R
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Producto
import com.example.panaderia.ui.theme.LilaPastel
import com.example.panaderia.viewmodel.InicioViewModel


@Composable
// La función recibe de argumento la lista de productos.
fun ListaCatalogo(productos: List<Producto>, viewModel: InicioViewModel = viewModel()){
    // Contenedor caja externo, (Acá le damos las dimenciones máximas a la lista.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Por cada producto en la lista.
        for (p in productos){
            // Contenedor caja para todo el componente.
            Box(
                modifier = Modifier
                    .background(LilaPastel)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ){
                // Contenedor columna
                Row(){
                    // Contenedor carta para la imagen.
                    Card(){
                        // Imagen dentro del contenedor carta.
                        Image(
                            painter = rememberAsyncImagePainter(p.url), // Referenciamos el url del producto actual.
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 120.dp, height = 80.dp) // Seteamos el tamaño.
                                .padding(12.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    // Los textos van dentro de una columna para aprovechar mejor el espacio.
                    Column(
                    ){
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
                }
            }
        }
    }
}