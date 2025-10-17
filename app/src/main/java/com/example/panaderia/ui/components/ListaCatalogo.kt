package com.example.panaderia.ui.components

import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState  // Para hacer scroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll  // Para hacer scroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.guardarCarrito
import com.example.panaderia.repository.leerCarritos
import com.example.panaderia.ui.theme.AzulPastel
import com.example.panaderia.ui.theme.LilaPastel
import com.example.panaderia.ui.theme.Purple40
import com.example.panaderia.ui.theme.RojoPastel
import com.example.panaderia.viewmodel.CatalogoViewModel
import kotlinx.coroutines.launch


@Composable
// La función recibe de argumento la lista de productos.
fun ListaCatalogo(productos: List<Producto>, viewModel: CatalogoViewModel = viewModel()){

    // Cargamos el contexto.
    val contexto = LocalContext.current

    // Por ahora vamos a hardcodear el id del carrito, despues lo vamos a sacar del usuario
    val idCarrito = "1"



    // Contenedor caja externo, para centrar.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Contenedor interior para ordenar verticalmente.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(6.dp)
                .clip(RoundedCornerShape(6.dp))
                .verticalScroll(rememberScrollState()), // Para hacer scroll.
            horizontalAlignment = Alignment.Start
        ){
            // Por cada producto en la lista.
            for (p in productos){
                // Contenedor columna
                Column(modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))) {
                    // Imagen.
                    Image(
                        painter = rememberAsyncImagePainter(p.url), // Referenciamos el url del producto actual.
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 340.dp, height = 280.dp) // Seteamos el tamaño.
                            .padding(6.dp)
                            .background(color = Color.White)
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )
                    // Los textos van dentro de una columna para aprovechar mejor el espacio.
                    Column()
                    {
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
                        // Acá llamamos la funcion que crea el boton para agregar un producto al carrito
                        BotonAgregarCarrito(idCarrito, p, viewModel)
                    }
                }
            }
        }

    }
}


// Funcion que crea el boton agregar al carrito
@Composable
fun BotonAgregarCarrito(idCarrito: String, producto: Producto, viewModel: CatalogoViewModel){
    // Cargamos el contexto.
    val contexto = LocalContext.current

    Box(
        modifier = Modifier
            .size(width = 160.dp, height = 60.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        Button(
            // El escuchador
            onClick = {
                viewModel.agregarProductoAlCarrito(contexto, producto, idCarrito)

            },
            shape = RoundedCornerShape(size = 6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple40),

            ) {
            // Texto dentro del boton.
            Text(text = "Agregar al carrito")
        }
    }
}