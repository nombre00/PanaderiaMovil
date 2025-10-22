package com.example.panaderia.ui.components


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.R
import kotlinx.coroutines.launch

// --- Las funciones para guardar y cargar la URI se mantienen en español ---
fun guardarUri(contexto: Context, uri: Uri) {
    val preferencias = contexto.getSharedPreferences("panaderia_prefs", Context.MODE_PRIVATE)
    preferencias.edit().putString("uri_foto_perfil", uri.toString()).apply()
}

fun cargarUri(contexto: Context): Uri? {
    val preferencias = contexto.getSharedPreferences("panaderia_prefs", Context.MODE_PRIVATE)
    val uriEnString = preferencias.getString("uri_foto_perfil", null)
    return uriEnString?.let { Uri.parse(it) }
}


@Composable
fun SelectorFotoPerfil() {
    val contexto = LocalContext.current
    var uriDeLaImagen by remember { mutableStateOf(cargarUri(contexto)) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            uriDeLaImagen = it
            guardarUri(contexto, it)
        }
    }

    // 2. Lanzador que pide el permiso.
    val lanzadorDePermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { fueConcedido: Boolean ->
        if (fueConcedido) {
            // Si el usuario concede el permiso, ahora abrimos la galería.
            lanzadorGaleria.launch("image/*")
        } else {
            scope.launch {
                snackbarHostState.showSnackbar("Permiso denegado. No se puede seleccionar una foto.")
            }
        }
    }

    val permisoRequerido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Columna principal que contendrá la imagen, el botón y el Snackbar.
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contenedor para la imagen y el botón
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = uriDeLaImagen ?: R.drawable.perfil_por_defecto
                ),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable {
                        // Comprobamos si ya tenemos el permiso.
                        when (ContextCompat.checkSelfPermission(contexto, permisoRequerido)) {
                            PackageManager.PERMISSION_GRANTED -> {
                                // Si ya tenemos permiso, abrimos la galería.
                                lanzadorGaleria.launch("image/*")
                            }
                            else -> {
                                // Si no, lo solicitamos.
                                lanzadorDePermisos.launch(permisoRequerido)
                            }
                        }
                    },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                when (ContextCompat.checkSelfPermission(contexto, permisoRequerido)) {
                    PackageManager.PERMISSION_GRANTED -> {
                        lanzadorGaleria.launch("image/*")
                    }
                    else -> {
                        lanzadorDePermisos.launch(permisoRequerido)
                    }
                }
            }) {
                Text("Cambiar Foto de Perfil")
            }
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(16.dp))
    }
}