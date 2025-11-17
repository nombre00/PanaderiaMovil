package com.example.panaderia.ui.components

import android.graphics.fonts.Font
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panaderia.model.Carrito


// Lista que resume productos en carrito, se muestra en perfil
@Composable
fun ListaResumenCarrito(carrito: Carrito, titulo: String){



    // Variable local que muestra el precio del carrito.
    var precioTotal = 0

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(RoundedCornerShape(32.dp))
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(22.dp)
        ) {
            Text(text = titulo, modifier = Modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold)



            if (carrito.id != 0){
                if (carrito.productos.isEmpty()){
                    Text(text = "No hay prodcutos en el carrito")
                } else {
                    // Calculamos el total.
                    for (producto in carrito.productos){
                        precioTotal += producto.precio
                    }
                    // Mostramos el total con un texto.
                    Text(text = "Pecio total: $" + precioTotal + " pesos",
                        modifier = Modifier, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                    for (producto in carrito.productos){
                        Row {
                            Text(text = " Nombre: " + producto.nombre)
                            Text(text = " Precio: $" + producto.precio)
                        }
                    }
                }
            } else {
                Text(text = "No hay prodcutos en el carrito")
            }
        }
    }
}