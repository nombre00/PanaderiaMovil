package com.example.panaderia.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.guardarCarrito
import com.example.panaderia.repository.leerCarritos
import com.example.panaderia.ui.theme.Azul1
import com.example.panaderia.ui.theme.AzulPastel
import com.example.panaderia.ui.theme.Gris1
import com.example.panaderia.ui.theme.LilaPastel
import com.example.panaderia.ui.theme.Purple40
import com.example.panaderia.ui.theme.RojoPastel
import com.example.panaderia.ui.theme.Verde1
import com.example.panaderia.ui.theme.VerdePastel
import com.example.panaderia.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch


@Composable
fun ListaCarrito(carrito: Carrito, viewModel: CarritoViewModel = viewModel(), precio: Int){

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
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(6.dp))
                .verticalScroll(rememberScrollState()), // Para hacer scroll.
            //horizontalAlignment = Alignment.Start
        ) {
            // Acá van a ir elementos arriba de la lista, como el boton comprar u otras cosas.
            BarraOpciones(carrito, viewModel, precio)
            Spacer(modifier = Modifier.height(24.dp))

            // Por cada producto dentro del carrito tenemos una fila con los datos del producto.
            carrito.productos.forEach { p ->
                CardCarrito(p, carrito, viewModel)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


// Funcion que crea la barra superior que contiene el precio total y botones.
@Composable
fun BarraOpciones(carrito: Carrito, viewModel: CarritoViewModel = viewModel(), precio: Int){
    Box(
        modifier = Modifier.fillMaxWidth().height(80.dp)
            .background(color = Color.White)
            .border(
                width = 1.dp,
                color = Gris1,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center,

    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            // Precio
            Text(text = "Precio total: $${precio}", fontWeight = FontWeight.Bold)

            // Boton comprar
            Box(
                modifier = Modifier
                    .size(width = 120.dp, height = 60.dp)

            ){
                val contexto = LocalContext.current
                Button(
                    // El escuchador
                    onClick = {viewModel.comprar(contexto, carrito)},
                    shape = RoundedCornerShape(size = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Verde1),

                    ) {
                    // Texto dentro del boton.
                    Text(text = "Comprar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


// Funcion que crea un Card que contiene el pedido.
@Composable
fun CardCarrito(p: Producto, carrito: Carrito, viewModel: CarritoViewModel){
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Gris1,
                shape = RoundedCornerShape(20.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Imagen.
        Image(
            painter = rememberAsyncImagePainter(p.url), // Referenciamos el url del producto actual.
            contentDescription = null,
            modifier = Modifier
                .size(width = 90.dp, height = 70.dp) // Seteamos el tamaño.
                .padding(6.dp)
                .clip(RoundedCornerShape(24.dp))
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
        // Boton eliminar del carrito.
        BotonEliminar(p, carrito , viewModel)
    }

}



// Funcion que crea el boton eliminar.
@Composable
fun BotonEliminar(producto: Producto, carrito: Carrito, viewModel: CarritoViewModel){

    // Creamos una variable para manejar el contexto donde se encuentra el botón,
    val contexto = LocalContext.current.applicationContext

    Box(
        modifier = Modifier
            .padding(6.dp)
    ){
        Button(
            // El escuchador
            onClick = {
                // Toast es un mensaje que aparece.
                Toast.makeText(contexto, "Producto eliminado del carrito.", Toast.LENGTH_SHORT).show()

                // Eliminar el producto del carrito llamando a la funcion desde viewmodel.
                viewModel.eliminarProductoCarrito(contexto, producto.id, carrito.id)
            },
            shape = RoundedCornerShape(size = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Azul1),

            ) {
            // Texto dentro del boton.
            Text(text = "Borrar")
        }
    }
}


