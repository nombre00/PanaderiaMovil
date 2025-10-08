package com.example.panaderia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.model.Producto
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.ListaCatalogo
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.InicioViewModel


@Composable
fun Catalogo(viewModel: InicioViewModel = viewModel()){
    panaderia(){
        CatalogoScaffold()
    }
}

// Funcion que crea el scaffold.
@Composable
fun CatalogoScaffold(){

    // Lista de productos.
    /*catalogoProductos.push({id:"kuchen_M",nombre:"Kuchen de manzana",precio:19000,imagen:"https://cdn0.recetasgratis.net/es/posts/2/9/2/kuchen_de_manzana_facil_y_rapido_45292_orig.jpg"});
    catalogoProductos.push({id:"mil_hoja",nombre:"Torta de Milhoja",precio:16000,imagen:"https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg"});
    catalogoProductos.push({id:"strudel",nombre:"Strudel de manzana",precio:21000,imagen:"https://www.puffpastry.com/wp-content/uploads/2017/02/BH_7284.jpg"});
    catalogoProductos.push({id:"t_3_leches",nombre:"Torta tres leches",precio:18000,imagen:"https://wattsindustrial.cl/wp-content/uploads/2023/03/tortatresleches-min.jpeg"});
    catalogoProductos.push({id:"p_limon",nombre:"Pie de limón",precio:18000,imagen:"https://images.unsplash.com/photo-1681329142517-6daaa56d3670?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"});
    catalogoProductos.push({id:"kuchen_n",nombre:"Kuchen de nuez",precio:17000,imagen:"https://i0.wp.com/entreparrilleros.cl/wp-content/uploads/2024/02/Kuchen-de-nuez.png?fit=1080%2C1080&ssl=1"});
    catalogoProductos.push({id:"s_negra",nombre:"Selva negra",precio:16000,imagen:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkQeC-bVK7OKElOTJwbTTya0dxXdGm3iqBJA&s"});
    catalogoProductos.push({id:"t_choco",nombre:"Torta de Chocolate",precio:15000,imagen:"https://amoradulce.com/wp-content/uploads/2019/12/Torta-chocolate-1_04_13_2024-scaled.jpg"});
    catalogoProductos.push({id:"t_cafe",nombre:"Torta de Café",precio:15000,imagen:"https://www.littlesugarsnaps.com/wp-content/uploads/2021/05/Coffee-Flavoured-Cake-Featured-Image-8692.jpg"});
    catalogoProductos.push({id:"c_volteada",nombre:"Crema Volteada",precio:14000,imagen:"https://dellepiane.pe/wp-content/uploads/2023/08/crema-volteada-001.jpg"});*/

    // Por ahora creamos una lista de obejtos acá, despues vamos a referenciar local Storage.
    val productos = mutableListOf<Producto>()
    // Creamos y agregamos los productos a la lista.
    productos.add(Producto("kuchen_M", "Kuchen de manzana", 19000.0, "https://cdn0.recetasgratis.net/es/posts/2/9/2/kuchen_de_manzana_facil_y_rapido_45292_orig.jpg"))
    productos.add(Producto("mil_hoja", "Torta de Milhoja", 16000.0, "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg"))

    Scaffold(
        // Footer.
        bottomBar = { Footer() },
        // Caja que contiene la imagen de fondo.
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
            {
                // Imagen de fondo.
                ImagenFondo(
                    modifier = Modifier.padding(paddingValues)
                )
                // Título de la pagina
                Titulo(titulo = "Catálogo")
            }
            // Lista. (Puede que falte algo, un cuadro transparente para separar la lista del fondo).
            ListaCatalogo(productos)
        }
    )
}