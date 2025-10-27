package com.example.panaderia.ui.components


import android.Manifest // Importa los permisos del archivo manifest.
import android.app.Activity // Sirve para volver a la actividad actual si no se selecciona una foto.
import android.content.Context
import android.content.pm.PackageManager // Verifica si un permiso está concevido
import android.net.Uri // La ubicacion de una imagen
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
fun guardarUri(contexto: Context, uri: Uri) { // Funcion que guarda la imagen como string en SharedPreferences
    val preferencias = contexto.getSharedPreferences("panaderia_prefs", Context.MODE_PRIVATE)
    preferencias.edit().putString("uri_foto_perfil", uri.toString()).apply() // Guarda de forma asincrona rapido
}

fun cargarUri(contexto: Context): Uri? { // Carga la uri guardad
    val preferencias = contexto.getSharedPreferences("panaderia_prefs", Context.MODE_PRIVATE)
    val uriEnString = preferencias.getString("uri_foto_perfil", null)
    return uriEnString?.let { Uri.parse(it) } // Si no la encuentra da un valor nulo
}


@Composable
fun SelectorFotoPerfil() {
    val contexto = LocalContext.current // Contexto local
    var uriDeLaImagen by remember { mutableStateOf(cargarUri(contexto)) } // Escuchador de la imagen como flujo de estado

    val scope = rememberCoroutineScope() // Para lanzar corrutinas.
    val snackbarHostState = remember { SnackbarHostState() } // Controla el snackbar, mensajes flotantes, seran popups


    // Funcion/variable que abre la carpeta externa del dispositivo
    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) { // si se selecciona una imagen
            uriDeLaImagen = uri
            guardarUri(contexto, uri)
        } else { // Si no se selecciona una imagen
            // Salir de la actividad si no se seleccionó imagen
            (contexto as? Activity)?.finish()
        }
    }

    // 2. Lanzador que pide el permiso.
    val lanzadorDePermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { fueConcedido: Boolean ->
        if (fueConcedido) {
            // Si el usuario concede el permiso, ahora abrimos la galería.
            lanzadorGaleria.launch("image/*")
        } else { // Esto aparece al no tener los permisos
            scope.launch {
                snackbarHostState.showSnackbar("Permiso denegado. No se puede seleccionar una foto.")
            }
        }
    }

    val permisoRequerido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Permiso segun verison de android
        Manifest.permission.READ_MEDIA_IMAGES // Verison 13
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE // Versiones anteriores
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
                painter = rememberAsyncImagePainter( // Contenedor de la imagen seleccionada.
                    model = uriDeLaImagen ?: R.drawable.perfil_por_defecto
                ),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable {  // Tambien funciona como boton que desencadena la accion
                        // Comprobamos si ya tenemos el permiso.
                        when (ContextCompat.checkSelfPermission(contexto, permisoRequerido)) {
                            PackageManager.PERMISSION_GRANTED -> {  // Revisa si se dieron los permisos
                                // Si ya tenemos permiso, abrimos la galería.
                                lanzadorGaleria.launch("image/*") // El string es la ruta de la carpeta
                            }
                            else -> { // Else agregado para que el programa no se quede pegado cuando no se selecciona una imagen.
                                // Si no, lo solicitamos.
                                lanzadorDePermisos.launch(permisoRequerido)
                            }
                        }
                    },
                contentScale = ContentScale.Crop // Recorta la imagen
            )

            Spacer(modifier = Modifier.height(20.dp))  // Espaciador

            Button(onClick = {  // El boton desencadena la accion
                when (ContextCompat.checkSelfPermission(contexto, permisoRequerido)) {
                    PackageManager.PERMISSION_GRANTED -> { // Revisa si se dieron los permisos
                        lanzadorGaleria.launch("image/*") // El string es la ruta de la carpeta
                    }
                    else -> {
                        lanzadorDePermisos.launch(permisoRequerido)
                    }
                }
            }) {
                Text("Cambiar Foto de Perfil")
            }
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(16.dp)) // muestra un popup, creo
    }
}