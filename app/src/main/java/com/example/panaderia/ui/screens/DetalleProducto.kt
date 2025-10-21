package com.example.panaderia.ui.screens


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Producto
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.theme.Gris2
import com.example.panaderia.viewmodel.DetalleProductoViewModel


// Al hacerle click a un producto en el catalogo nos manda a esta pagina que contiene más datos del producto.
@Composable
fun DetalleProducto(viewModel: DetalleProductoViewModel = DetalleProductoViewModel(), controladorNav: NavHostController){

    // Variable para acceder al contexto.
    val contexto = LocalContext.current

    // Revisamos el estado del producto
    val productoDetalle by viewModel.detalleProducto.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.cargarDetalleProducto(contexto)
    }


    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                // Imagen de fondo.
                ImagenProducto(productoDetalle)
                // Targeta con info
                Column(modifier = Modifier){
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier

                            .height( 320.dp)
                            .fillMaxWidth()
                            .background(color = Color.White)) {

                        Column(modifier = Modifier.fillMaxWidth().padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally){
                            // Nombre
                            Text(text = productoDetalle.nombre, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                            // precio.
                            Text(text = "Precio: $" + productoDetalle.precio, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        // Receña.
                        Text(text = "Reseña:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = productoDetalle.resena)
                        Spacer(modifier = Modifier.weight(1f))
                        // Boton volver.
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                            Button(onClick = { controladorNav.navigate("Catalogo") }){ Text("Volver") }
                        }
                    }
                }
            }
        }
    )
}

// Componente que contiene la imagen de fondo, que es una imagen del producto.
@Composable
fun ImagenProducto(producto: Producto){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = rememberAsyncImagePainter(producto.url),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop  // Ajusta la imagen al contenedor.
        )
    }
}