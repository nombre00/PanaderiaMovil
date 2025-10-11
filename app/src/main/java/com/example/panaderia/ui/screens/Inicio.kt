package com.example.panaderia.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.guardarCatalogo
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.Titulo
import kotlinx.coroutines.launch

@Composable
fun Inicio(viewModel: InicioViewModel = viewModel()) {
    // Estado de la pagina (creo que se usa para comunicarse con otros componentes)
    val mensaje by viewModel.message.collectAsState() // Observa el estado

    panaderia {
       InicioScaffold()
    }
}

// Funcion que crea el scaffold.
@Composable
fun InicioScaffold(){
    Scaffold(
        // Footer.
        bottomBar = { Footer() },
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
                Column {
                    // contenido que va encima de la imagen.
                    Titulo(titulo = "Inicio")






                    // Esto lo hacemos acá ya que para acceder a un contexto tenemos que hacerlo desde un componente @Composable, este es el primer componente que cargamos
                    // Lo primero que vamos a hacer es crear una lista de productos que vamos a guardar en local storage.
                    // Lista de productos.
                    // Por ahora creamos una lista de obejtos acá, despues vamos a referenciar local Storage.
                    val productos = mutableListOf<Producto>()
                    // Creamos y agregamos los productos a la lista.
                    productos.add(Producto("kuchen_M", "Kuchen de manzana", 19000, "https://cdn0.recetasgratis.net/es/posts/2/9/2/kuchen_de_manzana_facil_y_rapido_45292_orig.jpg"))
                    productos.add(Producto("mil_hoja", "Torta de Milhoja", 16000, "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg"))
                    productos.add(Producto("strudel", "Strudel de manzana", 21000, "https://www.puffpastry.com/wp-content/uploads/2017/02/BH_7284.jpg"))
                    productos.add(Producto("t_3_leches", "Torta tres leches", 18000, "https://wattsindustrial.cl/wp-content/uploads/2023/03/tortatresleches-min.jpeg"))
                    productos.add(Producto("p_limon", "Pie de limón", 18000, "https://images.unsplash.com/photo-1681329142517-6daaa56d3670?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                    productos.add(Producto("kuchen_n", "Kuchen de nuez", 17000, "https://i0.wp.com/entreparrilleros.cl/wp-content/uploads/2024/02/Kuchen-de-nuez.png?fit=1080%2C1080&ssl=1"))
                    productos.add(Producto("s_negra", "Selva negra", 16000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkQeC-bVK7OKElOTJwbTTya0dxXdGm3iqBJA&s"))
                    productos.add(Producto("t_choco", "Torta de Chocolate", 15000, "https://amoradulce.com/wp-content/uploads/2019/12/Torta-chocolate-1_04_13_2024-scaled.jpg"))
                    productos.add(Producto("t_cafe", "Torta de Café", 15000, "https://www.littlesugarsnaps.com/wp-content/uploads/2021/05/Coffee-Flavoured-Cake-Featured-Image-8692.jpg"))
                    productos.add(Producto("c_volteada", "Crema Volteada", 14000, "https://dellepiane.pe/wp-content/uploads/2023/08/crema-volteada-001.jpg"))
                    // Creamos una variable para manipular el contexto local.
                    val contexto = LocalContext.current

                    // Variable para manejar las corrutinas, llamo las funciones suspend con esto.
                    val coroutinesScope = rememberCoroutineScope()
                    // Guardamos los datos en local storage
                    Box(modifier = Modifier.size(height = 50.dp, width = 220.dp)){
                        Button(onClick = {
                            coroutinesScope.launch {
                                // Guardamos la lista en la base de datos.
                                guardarCatalogo(contexto, productos)
                            }
                        }) { Text("Cargar base de datos") }
                    }
                }
            }
        }
    )
}