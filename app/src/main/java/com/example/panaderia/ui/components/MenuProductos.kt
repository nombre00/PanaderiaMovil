package com.example.panaderia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.panaderia.ui.theme.Azul2
import com.example.panaderia.ui.theme.AzulPastel
import com.example.panaderia.viewmodel.CatalogoViewModel


@Composable
fun MenuProductos(viewModel: CatalogoViewModel){
    // Botones para filtar el catalogo y quizas el carrito.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { viewModel.cambiarSeleccionProductos("") },
            colors = ButtonDefaults.buttonColors(containerColor = Azul2)
        ) { Text("Todos") }
        Button(
            onClick = { viewModel.cambiarSeleccionProductos("pan") },
            colors = ButtonDefaults.buttonColors(containerColor = Azul2)
        ) { Text("Pan") }
        Button(
            onClick = { viewModel.cambiarSeleccionProductos("torta") },
            colors = ButtonDefaults.buttonColors(containerColor = Azul2)
        ) { Text("Torta") }
        Button(
            onClick = { viewModel.cambiarSeleccionProductos("cafe") },
            colors = ButtonDefaults.buttonColors(containerColor = Azul2)
        ) { Text("Café") }
    }
}