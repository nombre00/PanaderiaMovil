package com.example.panaderia.ui.components


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset.Companion.Unspecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.panaderia.R
import com.example.panaderia.viewmodel.LoginViewModel


@Composable
fun FormularioLogin(controladorNav: NavHostController, viewModel: LoginViewModel) {

    val contexto = LocalContext.current

    // Escuchadores de los estados de los inputs
    var mail by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var claveVisible by remember { mutableStateOf(false) }

    // Escuchamos los estados de los errores para mostrar mensajes de validacion
    var mailError by remember { mutableStateOf("") }
    var claveError by remember { mutableStateOf("") }

    // Tenemos que escuchar los estados si queremos que los cambios funcionen.
    LaunchedEffect(Unit) {
        viewModel.cargarClientes(contexto)
        viewModel.cargarClienteIngresado(contexto)
    }



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(24.dp))

        // Input del mail
        TextField(
            value = mail, // Valor es lo que contiene, en este caso la variable mail contiene el input, y es un estado que escuchamos
            onValueChange = {mail = it}, // Cuando cambia el valor dentro del contenedor mostramos el contenedoer actualizado. contenedor = variable

            // Label muestra un texto default, el texto default es el error, y si el error está vacio muestra 'mail'
            // Tambien pusimos que si mailError no está vacio, eso se ve rojo
            label = { Text(mailError.ifEmpty { "mail" }, color = if (mailError.isNotEmpty()) Color.Red else Color.Unspecified) },
            leadingIcon = { // Es el icono que aparece antes del texto.
                Icon( Icons.Rounded.AccountCircle, contentDescription = "" )
            },
            shape = RoundedCornerShape(8.dp), // Redondeamos los bordes
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            /** colors = TextFieldDefaults.colors( // Seteamos los colores default
                focusedTextColor = Transparent,
                unfocusedIndicatorColor = Transparent
            ) */
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input de la clave
        TextField(
            value = clave,
            onValueChange = {clave = it},
            label = { Text(claveError.ifEmpty { "clave" }, color = if (claveError.isNotEmpty()) Color.Red else Color.Unspecified) },
            leadingIcon = { // Es el icono que aparece antes del texto.
                Icon( Icons.Rounded.Lock, contentDescription = "" )
            },
            // Abajo usamos clave visible para transformar el texto de visible a no visible
            visualTransformation = if (claveVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                var imagen = if (claveVisible) {  // Cambiamos la imagen acorde al estado
                painterResource(id = R.drawable.iconovisible)
                } else {
                painterResource(id = R.drawable.icononovisible)
                }
                Icon(  // Imagen
                    painter = imagen,
                    contentDescription = ""
                )
            },
            shape = RoundedCornerShape(8.dp), // Redondeamos los bordes
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            /** colors = TextFieldDefaults.colors( // Seteamos los colores default
                focusedTextColor = Transparent,
                unfocusedIndicatorColor = Transparent
            ) */
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Boton de login, revisa que los textos tengan contenido
        Button(
            onClick = {
                mailError = if (mail.isBlank()) "Ingrese un mail" else ""
                claveError = if (clave.isBlank()) "Ingrese su clave" else ""

                if (mailError.isEmpty() && claveError.isEmpty()){
                    // Logica, falta

                    val bandera = viewModel.loginIngresar(contexto, mail, clave)
                    if (bandera){ // Si el ingreso es exitoso vamos a la pagina perfil
                        controladorNav.navigate("Perfil")
                    } else { // Sino un toast nos dice que no se encontró el cliente y que nos registremos
                        Toast.makeText(contexto, "El cliente no existe, regístrese", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 80.dp)
        ) { Text( text = "Login" ) }

        Spacer(modifier = Modifier.height(8.dp))

        // Registrarse.
        Row(modifier = Modifier.background(color = Color.White)) {
            Text( text = "No está resgistrado? " )
            Text( text = "Registrarse",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    // Nos enruta a registrarse.
                    controladorNav.navigate("Registrarse")
                },
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Boton volver.
        Button(
            onClick = {
                // Volver a la pagina anterior
                controladorNav.navigate("Inicio")
            }
        ) { Text( text = "Volver" ) }
    }
}