package com.example.panaderia.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Cliente
import com.example.panaderia.viewmodel.InicioViewModel


@Composable
fun PromocionDia(viewModel: InicioViewModel, cliente: Cliente, controladorNav: NavHostController){

    // Cargamos el contexto.
    val contexto = LocalContext.current

    Column(modifier = Modifier
        .size(400.dp)
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Text(text = "Promocion del día, 10% descuento!",
            modifier = Modifier.background(color = Color.White),
            fontWeight = FontWeight.Bold)
        Image(
            painter = rememberAsyncImagePainter("https://www.puffpastry.com/wp-content/uploads/2017/02/BH_7284.jpg"),
            contentDescription = null,
            contentScale = ContentScale.Crop,  // Ajusta la imagen al contenedor.)
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
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