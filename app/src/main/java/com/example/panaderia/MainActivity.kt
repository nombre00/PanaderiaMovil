package com.example.panaderia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.panaderia.model.Producto

import com.example.panaderia.ui.screens.GestionCarrito
import com.example.panaderia.ui.screens.Catalogo
import com.example.panaderia.ui.screens.Inicio
import com.example.panaderia.ui.screens.Login
import com.example.panaderia.ui.screens.Perfil
import com.example.panaderia.ui.screens.Registrarse
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.CarritoViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Todo va despues de la línea de abajo, sino nos aparecen llamadas a componentes nulos.
        super.onCreate(savedInstanceState)





        // LLamamos a la spashScreen que creamos, se va a ver mientras carga el programa.
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            // Este va a ser el contenedor principal, acá va a ir el enrutador y lo demas se muestra por aca.
            panaderia {
                // Esta variable va a contener el estado que le indica al navegador a donde ir.
                val controladorNav = rememberNavController()
                // Scaffold va a ser nuestro contenedor más grande, lo demas va dentro de aca.
                Scaffold(
                    // Contiene el nombre de la tienda.
                    topBar = {
                        TopAppBar(title = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    "Panadería Dulce tradición",
                                    textAlign =  TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        })
                    },
                    // Contiene la nav-bar.
                    bottomBar = {
                        NavigationBar {
                            // Implementación de la navbar.
                            // Obtiene el estado actual de la pila de navegación (back stack) usando el NavController.
                            // Esto nos permite saber qué pantalla está activa en este momento.
                            val navBackStackEntry by controladorNav.currentBackStackEntryAsState()

                            // Extrae la ruta (route) de la pantalla actual a partir del estado de la pila.
                            // Si no hay entrada (por ejemplo, al inicio), currentRoute será null.
                            val currentRoute = navBackStackEntry?.destination?.route

                            // Primer ítem de la barra de navegación: representa la pantalla "Inicio".
                            NavigationBarItem(
                                // Determina si este ítem está seleccionado comparando la ruta actual con "inicio".
                                // Si son iguales, se resalta visualmente.
                                selected = currentRoute == "Inicio",
                                // Acción que se ejecuta al hacer clic: navega a la ruta "inicio".
                                onClick = { controladorNav.navigate("Inicio") },
                                // Define el ícono que se muestra (un ícono de casa de Material Design).
                                icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                                // Etiqueta de texto que aparece debajo del ícono.
                                label = { Text("Inicio") }
                            )

                            // Tercer ítem: representa la pantalla "Catálogo".
                            NavigationBarItem(
                                selected = currentRoute == "Catalogo", // Selecciona si la ruta es "catalogo".
                                onClick = { controladorNav.navigate("Catalogo") }, // Navega a "catalogo".
                                icon = { Icon(Icons.Default.ThumbUp, contentDescription = "Catálogo") }, // Ícono de tienda.
                                label = { Text("Catálogo") } // Etiqueta "Catálogo".
                            )

                            // Cuarto ítem: representa la pantalla "Carrito".
                            NavigationBarItem(
                                selected = currentRoute == "Carrito", // Selecciona si la ruta es "carrito".
                                onClick = { controladorNav.navigate("Carrito") }, // Navega a "carrito".
                                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") }, // Ícono de carrito.
                                label = { Text("Carrito") } // Etiqueta "Carrito".
                            )

                            // Quinto ítem: representa la pantalla "Perfil".
                            NavigationBarItem(
                                selected = currentRoute == "Perfil", // Selecciona si la ruta es "perfil".
                                onClick = { controladorNav.navigate("Perfil") }, // Navega a "perfil".
                                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }, // Ícono de persona.
                                label = { Text("Perfil") } // Etiqueta "Perfil".
                            )
                        }
                    },
                    content = { padding ->
                        NavHost(
                            navController = controladorNav,
                            startDestination = "Inicio",
                            modifier = Modifier.padding(padding)
                        ){
                            composable("Inicio") { Inicio( controladorNav = controladorNav) }
                            composable("Catalogo") { Catalogo() }
                            composable( "Carrito") { backStackEntry ->
                                val viewModel: CarritoViewModel = viewModel(backStackEntry)
                                GestionCarrito(viewModel = viewModel, idCarrito = "1")
                            }
                            composable("Perfil") { Perfil(controladorNav = controladorNav) }
                            // Rutas que no estan en el navbar, botones enrutan esto.
                            composable("Login") { Login(controladorNav = controladorNav) }
                            composable("Registrarse") {Registrarse(controladorNav = controladorNav)}
                        }
                    }
                )
            }
        }
    }
}


