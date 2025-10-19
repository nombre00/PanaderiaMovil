package com.example.panaderia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panaderia.model.Envio


// Lista que resume productos en envios, se muestra en perfil
@Composable
fun ListaResumenEnvios(envios: List<Envio>, titulo: String){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
    ){
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(22.dp)
        ) {
            // Primero las verificaciones.
            if (envios.isEmpty()){ // Si la lista de envios está vacia no hay envios asociados al cliente
                Text(text = "No hay envios agendados")
            } else {
                for (envio in envios){
                    CardEnvio(envio, titulo)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}



// Card que maneja cada envio.
@Composable
fun CardEnvio(envio: Envio, titulo: String){
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(22.dp)
        ) {
            Text(text = titulo, modifier = Modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            if (envio.productos.isEmpty()){ // O si el envio está vacio
                Text(text = "No hay prodcutos en el envio")
            } else {
                // Mostramos el estado del envio y la direccion.
                Text(text = "Estado: " + envio.estado, fontWeight = FontWeight.Bold)
                Text(text = "Direccion: " + envio.direccion, fontWeight = FontWeight.Bold)

                for (producto in envio.productos){
                    Row {
                        Text(text = " Nombre: " + producto.nombre)
                        Text(text = " Precio: $" + producto.precio)
                    }
                }
            }
        }
    }
}