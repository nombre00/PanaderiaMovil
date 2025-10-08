package com.example.panaderia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.R
import com.example.panaderia.viewmodel.InicioViewModel


@Composable
fun Footer(viewModel: InicioViewModel = viewModel()){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.footer)) // Agregamos el color del footer.
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        // Columna para organizar los elementos.
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Contáctanos",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = "Panadería Dulce tradición",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(
                text = "Dirección: Calle Valparaíso 123, Viña del Mar",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Text(
                text = "Teléfono: +34 555 123 456",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Text(
                text = "Email: contacto@dulceTradicion.com",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Text(
                text = "© 2025 Panadería Dulce Tradición. Todos los derechos reservados.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
        }
    }
}