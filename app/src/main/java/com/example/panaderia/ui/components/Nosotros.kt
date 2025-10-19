package com.example.panaderia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun Nosotros(){
    Column(modifier = Modifier.size(width = 380.dp, height = 1000.dp)) {
        // Acá van elementos visuales para rellenar inicio: una historia y una imagen.
        Image(
            painter = rememberAsyncImagePainter("https://i.pinimg.com/474x/a8/5f/4f/a85f4f28ea00e956e9905a23db8efb44.jpg"),
            contentDescription = null,
            modifier = Modifier.size(width = 360.dp, height = 440.dp).clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop  // Ajusta la imagen al contenedor.)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(// Podriamos animar este texto para que se agrande y achique con estilo.
            text = "Pastelería Dulce Tradición ha sido parte de la ciudad puerto desde sus mismos inicios " +
                    "cuando don Tradición fundó la pastelería desde su humilde y señorial palacio en calle Valparaíso. " +
                    "Junto a su señora, doña Tradición, empezaron un viaje de más de un siglo de sabor y dulzura para el " +
                    "barrio, la ciudad, y por qué no decirlo, el mundo.",
            modifier = Modifier.background(color = Color.White).fillMaxWidth().padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = rememberAsyncImagePainter("https://enterreno.s3.us-east-2.amazonaws.com/moments/5372_calle-valparaiso-de-via-del-mar-en-1925.jpg"),
            contentDescription = null,
            modifier = Modifier.size(width = 360.dp, height = 440.dp).clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop  // Ajusta la imagen al contenedor.)
        )
    }
}