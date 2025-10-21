package com.example.panaderia

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts

import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBarItem
import com.example.panaderia.ui.screens.Catalogo
import com.example.panaderia.ui.screens.GestionCarrito
import com.example.panaderia.ui.screens.Inicio
import com.example.panaderia.ui.screens.Login
import com.example.panaderia.ui.screens.Perfil
import com.example.panaderia.ui.screens.Registrarse
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.util.NotificacionHelper
import com.example.panaderia.viewmodel.CarritoViewModel

class MainActivity : ComponentActivity() {

    // Añado el lanzador dentro de la clase
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concedido
        } else {
            // Permiso denegado
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  llama a la funcion de permisos aka
        askNotificationPermission()

        NotificacionHelper.crearCanalDeNotificacion(this)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            panaderia {
                val controladorNav = rememberNavController()
                Scaffold(
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
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by controladorNav.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            NavigationBarItem(
                                selected = currentRoute == "Inicio",
                                onClick = { controladorNav.navigate("Inicio") },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                                label = { Text("Inicio") }
                            )

                            NavigationBarItem(
                                selected = currentRoute == "Catalogo",
                                onClick = { controladorNav.navigate("Catalogo") },
                                icon = { Icon(Icons.Default.ThumbUp, contentDescription = "Catálogo") },
                                label = { Text("Catálogo") }
                            )

                            NavigationBarItem(
                                selected = currentRoute == "Carrito",
                                onClick = { controladorNav.navigate("Carrito") },
                                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
                                label = { Text("Carrito") }
                            )

                            NavigationBarItem(
                                selected = currentRoute == "Perfil",
                                onClick = { controladorNav.navigate("Perfil") },
                                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                                label = { Text("Perfil") }
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
                            composable("Login") { Login(controladorNav = controladorNav) }
                            composable("Registrarse") {Registrarse(controladorNav = controladorNav)}
                        }
                    }
                )
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
