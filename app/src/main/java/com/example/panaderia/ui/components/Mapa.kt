package com.example.panaderia.ui.components

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@Composable
fun Mapa(onDismiss: () -> Unit) {
    val context = LocalContext.current  // Contexto local
    var userLocation by remember { mutableStateOf<LatLng?>(null) }  // recuerda las coordenadas del equipo
    // Usamos 'remember' para que no se cree el cliente en cada recomposición
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }  // Esto es para no duplicar datos.

    val permissionLauncher = rememberLauncherForActivityResult( // Pide los permisos
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            // Verificamos si alguno de los permisos de ubicación fue concedido
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                // Si tenemos permiso, obtenemos la última ubicación conocida
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        userLocation = LatLng(location.latitude, location.longitude)
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {  // Corrutina que espera los permisos.
        permissionLauncher.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    // El componente Dialog crea una ventana emergente
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().height(500.dp),
            shape = MaterialTheme.shapes.large
        ) {
            // Si ya tenemos la ubicación, mostramos el mapa
            if (userLocation != null) {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(userLocation!!, 15f)
                }
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = userLocation!!),
                        title = "Tu ubicación"
                    )
                }
            } else {
                // Si no, mostramos un indicador de carga
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}