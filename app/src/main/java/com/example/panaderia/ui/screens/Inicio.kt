package com.example.panaderia.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.InicioViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.guardarCarrito
import com.example.panaderia.repository.guardarCatalogo
import com.example.panaderia.ui.components.Carrusel
import com.example.panaderia.ui.components.CarruselCafe
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.Nosotros
import com.example.panaderia.ui.components.PromocionDia
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.Azul2
import com.example.panaderia.ui.theme.Rojo1
import com.example.panaderia.ui.theme.Verde1
import kotlinx.coroutines.launch
import com.example.panaderia.R

@Composable
fun Inicio(viewModel: InicioViewModel = viewModel()) {


    panaderia {
       InicioScaffold(viewModel)
    }
}

// Funcion que crea el scaffold.
@Composable
fun InicioScaffold(viewModel: InicioViewModel){
    Scaffold(
        // Caja que contiene la imagen de fondo.
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                // Imagen de fondo.
                ImagenFondo(
                    modifier = Modifier.padding(paddingValues)
                )
                // Columna para ordenar.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Contenido visual de la pagina.
                    // Que queremos mostrar.
                    // Productos: Un contenedor con imagenes animables.
                    // Ofertas:
                    // Fechas importantes:
                    // contenido que va encima de la imagen.
                    //Titulo(titulo = "Inicio")

                    Text(text = "Panes del mes",
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Verde1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Carrusel()

                    Spacer(modifier = Modifier.height(40.dp))

                    PromocionDia()

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(text = "Cafes de la casa",
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Verde1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CarruselCafe()

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(text = "Nosotros",
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Verde1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Nosotros()




                    // Empuja todo lo que viene después hacia abajo
                    Spacer(modifier = Modifier.weight(1f))
                    // Esto lo hacemos acá ya que para acceder a un contexto tenemos que hacerlo desde un componente @Composable, este es el primer componente que cargamos
                    // Lo primero que vamos a hacer es crear una lista de productos que vamos a guardar en local storage.
                    // Lista de productos.
                    // Por ahora creamos una lista de obejtos acá, despues vamos a referenciar local Storage.
                    val productos = mutableListOf<Producto>()
                    // Creamos y agregamos los productos a la lista.
                    // Tortas.
                    productos.add(Producto("kuchen_M", "Kuchen de manzana", 19000, "https://cdn0.recetasgratis.net/es/posts/2/9/2/kuchen_de_manzana_facil_y_rapido_45292_orig.jpg", "torta"))
                    productos.add(Producto("mil_hoja", "Torta de Milhoja", 16000, "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg", "torta"))
                    productos.add(Producto("strudel", "Strudel de manzana", 21000, "https://www.puffpastry.com/wp-content/uploads/2017/02/BH_7284.jpg", "torta"))
                    productos.add(Producto("t_3_leches", "Torta tres leches", 18000, "https://wattsindustrial.cl/wp-content/uploads/2023/03/tortatresleches-min.jpeg", "torta"))
                    productos.add(Producto("p_limon", "Pie de limón", 18000, "https://images.unsplash.com/photo-1681329142517-6daaa56d3670?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "torta"))
                    productos.add(Producto("kuchen_n", "Kuchen de nuez", 17000, "https://i0.wp.com/entreparrilleros.cl/wp-content/uploads/2024/02/Kuchen-de-nuez.png?fit=1080%2C1080&ssl=1", "torta"))
                    productos.add(Producto("s_negra", "Selva negra", 16000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkQeC-bVK7OKElOTJwbTTya0dxXdGm3iqBJA&s", "torta"))
                    productos.add(Producto("t_choco", "Torta de Chocolate", 15000, "https://amoradulce.com/wp-content/uploads/2019/12/Torta-chocolate-1_04_13_2024-scaled.jpg", "torta"))
                    productos.add(Producto("t_cafe", "Torta de Café", 15000, "https://www.littlesugarsnaps.com/wp-content/uploads/2021/05/Coffee-Flavoured-Cake-Featured-Image-8692.jpg", "torta"))
                    productos.add(Producto("c_volteada", "Crema Volteada", 14000, "https://dellepiane.pe/wp-content/uploads/2023/08/crema-volteada-001.jpg", "torta"))
                    // Panes.
                    productos.add(Producto("marraqueta", "Marraqueta", 300, "https://comerciante.lacuarta.com/wp-content/uploads/2022/05/Marraqueta-Tema-1-ok.jpg", "pan"))
                    productos.add(Producto("panhuevo", "Pan de huevo", 300, "https://www.tipicochileno.cl/wp-content/uploads/2021/05/pan-de-huevo-1200-628.jpg", "pan"))
                    productos.add(Producto("dobladita", "Dobladita de queso", 400, "https://tiendaelchileno.com/wp-content/uploads/2024/12/dobladita-pagina-web.jpeg", "pan"))
                    productos.add(Producto("medialuna", "Medialuna", 600, "https://cocinerosargentinos.com/content/recipes/original/recipes.12138.jpeg", "pan"))
                    // Cafes.
                    productos.add(Producto("late", "Café latte", 2100, "https://images.ctfassets.net/0e6jqcgsrcye/53teNK4AvvmFIkFLtEJSEx/4d3751dcad227c87b3cf6bda955b1649/Cafe_au_lait.jpg", "cafe"))
                    productos.add(Producto("americano", "Café americano", 1800, "https://excelso77.com/wp-content/uploads/2024/05/por-que-el-cafe-americano-se-llama-asi-te-lo-contamos.webp", "cafe"))
                    productos.add(Producto("moca", "Café mocaccino", 2600, "https://www.cabucoffee.com/newimages/Cafe-Moca.jpg", "cafe"))
                    productos.add(Producto("helado", "Café helado", 3200, "https://osterstatic.reciperm.com/webp/10230.webp", "cafe"))

                    // Creamos una variable para manipular el contexto local.
                    val contexto = LocalContext.current


                    // Creamos una lista de carritos.
                    val carritos = mutableListOf<Carrito>()
                    // Creamos y agregamos los carritos.
                    carritos.add(Carrito(id = "1", productos = mutableListOf()))


                    // Variable para manejar las corrutinas, llamo las funciones suspend con esto.
                    val coroutinesScope = rememberCoroutineScope()
                    // Guardamos los datos en local storage
                    Box(modifier = Modifier.size(height = 50.dp, width = 220.dp)){
                        Button(onClick = {
                            coroutinesScope.launch {
                                // Guardamos la lista en la base de datos.
                                guardarCatalogo(contexto, productos)
                                guardarCarrito(contexto, carritos)
                                Toast.makeText(contexto, "carritos ${carritos.size}", Toast.LENGTH_SHORT).show()
                            }
                        }) { Text("Cargar base de datos") }
                    }
                    Footer()
                }
            }
        }
    )
}