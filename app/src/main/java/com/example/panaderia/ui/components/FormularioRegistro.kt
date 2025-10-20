package com.example.panaderia.ui.components

import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.panaderia.R
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.Producto
import com.example.panaderia.viewmodel.RegistrarseViewModel


@Composable
fun FormularioRegistro(controladorNav: NavHostController, viewModel: RegistrarseViewModel){

    // Variable que captura el contexto.
    val contexto = LocalContext.current

    // Necesitamos escuchar los estados de los clientes y los carritos
    // Cargamos todos los carritos
    val listaCarritos by viewModel.carritos.collectAsStateWithLifecycle()
    // Cargamos todos los clientes
    val listaClientes by viewModel.clientes.collectAsStateWithLifecycle()

    // Corrutina
    LaunchedEffect(Unit) {
        viewModel.cargarCarritos(contexto)
        viewModel.cargarClientes(contexto)
    }


    // Escuchadores de los estados de los inputs.
    var nombre by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var confirmaClave by remember { mutableStateOf("") }
    var claveVisible by remember { mutableStateOf(false) }
    var confirmaClaveVisible by remember { mutableStateOf(false) }

    // Escuchamos los estados de los errores para mostrar mensajes de validacion
    var nombreError by remember { mutableStateOf("") }
    var mailError by remember { mutableStateOf("") }
    var direccionError by remember { mutableStateOf("") }
    var claveError by remember { mutableStateOf("") }
    var confirmarClaveError by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Registrarse", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(24.dp))

        Text( text = "Crear cuenta",
            fontSize = 22.sp,
            modifier = Modifier.fillMaxWidth().padding(start = 25.dp, top = 15.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
            )

        Spacer(modifier = Modifier.height(12.dp))

        Text( text = "Por favor ingrese sus datos:",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth().padding(start = 25.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input del nombre
        TextField(
            value = nombre, // Valor es lo que contiene, en este caso la variable nombre contiene el input, y es un estado que escuchamos
            onValueChange = {nombre = it}, // Cuando cambia el valor dentro del contenedor mostramos el contenedoer actualizado. contenedor = variable

            // Label muestra un texto default, el texto default es el error, y si el error está vacio muestra 'nombre'
            // Tambien pusimos que si nombreError no está vacio, eso se ve rojo
            label = { Text(nombreError.ifEmpty { "nombre" }, color = if (nombreError.isNotEmpty()) Color.Red else Color.Unspecified) },
            leadingIcon = { // Es el icono que aparece antes del texto.
                Icon( Icons.Rounded.AccountCircle, contentDescription = "" )
            },
            shape = RoundedCornerShape(8.dp), // Redondeamos los bordes
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors( // Seteamos los colores default
                focusedTextColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

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
            colors = TextFieldDefaults.colors( // Seteamos los colores default
                focusedTextColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input de la direccion
        TextField(
            value = direccion, // Valor es lo que contiene, en este caso la variable direccion contiene el input, y es un estado que escuchamos
            onValueChange = {direccion = it}, // Cuando cambia el valor dentro del contenedor mostramos el contenedor actualizado. contenedor = variable

            // Label muestra un texto default, el texto default es el error, y si el error está vacio muestra 'direccion'
            // Tambien pusimos que si mailError no está vacio, eso se ve rojo
            label = { Text(direccionError.ifEmpty { "direccion" }, color = if (direccionError.isNotEmpty()) Color.Red else Color.Unspecified) },
            leadingIcon = { // Es el icono que aparece antes del texto.
                Icon( Icons.Rounded.Home, contentDescription = "" )
            },
            shape = RoundedCornerShape(8.dp), // Redondeamos los bordes
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors( // Seteamos los colores default
                focusedTextColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
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
            colors = TextFieldDefaults.colors( // Seteamos los colores default
                focusedTextColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input de confirmarClave
        TextField(
            value = confirmaClave,
            onValueChange = {confirmaClave = it},
            label = { Text(confirmarClaveError.ifEmpty { "confirme su clave" }, color = if (confirmarClaveError.isNotEmpty()) Color.Red else Color.Unspecified) },
            leadingIcon = { // Es el icono que aparece antes del texto.
                Icon( Icons.Rounded.Lock, contentDescription = "" )
            },
            // Abajo usamos clave visible para transformar el texto de visible a no visible
            visualTransformation = if (confirmaClaveVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                var imagen = if (confirmaClaveVisible) {  // Cambiamos la imagen acorde al estado
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
            colors = TextFieldDefaults.colors( // Seteamos los colores default
                focusedTextColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Boton de login, revisa que los textos tengan contenido
        Button(
            onClick = {
                nombreError = if (mail.isBlank()) "Ingrese su nombre" else ""
                mailError = if (mail.isBlank()) "Ingrese un mail"
                    // Si el mail tiene contenido revisamos que sea un mail valido
                    else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) "Ingrese una dirección de mail válida"
                    else ""
                direccionError = if (mail.isBlank()) "Ingrese su direccion" else ""
                claveError = if (clave.isBlank()) "Ingrese su clave" else ""
                confirmaClave = if (mail.isBlank()) "Reingrese la clave"
                    else if (clave != confirmaClave) "Las claves ingresadas deben ser iguales"
                    else ""

                // Acá vamos a tener que probar
                if (mailError.isEmpty() && claveError.isEmpty() && nombreError.isEmpty() && direccionError.isEmpty() && confirmarClaveError.isEmpty()){



                    // Hechas todas las validaciones, creamos un nuevo cliente y lo guardamos.
                    // Tenemos que revisar el id de los clientes y de los carritos conseguir el maximo y sumarle uno.
                    var maxIdCliente = 0 // El valor parte en 0
                    var maxIdCarrito = 0 // El valor parte en 0
                    if (listaClientes.isEmpty()){ // Si la lista está vacia
                        maxIdCliente = 1 // El valor es 1
                    } else {  // Si no
                        for (cliente in listaClientes){ // Por cada iteracion
                            if (cliente.id.toInt() >= maxIdCliente){
                                maxIdCliente = cliente.id.toInt() // Igualamos el valor al valor maximo encontrado
                            }
                        }
                        maxIdCliente += 1 // Obtenido el valor maximo le sumamos 1
                    }
                    if (listaCarritos.isEmpty()){ // Si la lista está vacia
                        maxIdCarrito = 1 // El valor es 1
                    } else {  // Si no
                        for (carrito in listaCarritos){ // Por cada iteracion
                            if (carrito.id.toInt() >= maxIdCarrito){
                                maxIdCarrito = carrito.id.toInt() // Igualamos el valor al valor maximo encontrado
                            }
                        }
                        maxIdCarrito += 1 // Obtenido el valor maximo le sumamos 1
                    }


                    // Asignamos los valores
                    val cliente = Cliente(maxIdCliente.toString(), nombre, mail, direccion, clave, maxIdCarrito.toString(), emptyList() )
                    val carrito = Carrito(maxIdCarrito.toString(),maxIdCliente.toString(),emptyList<Producto>().toMutableList())
                    viewModel.crearCliente(contexto, cliente, carrito) // Creamos y guardamos el cliente

                    // Creado y guardado el cliente volvemos a login para ingresar.
                    controladorNav.navigate("Login")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 80.dp)
        ) { Text( text = "Registrarme" ) }

        Spacer(modifier = Modifier.height(8.dp))

        // Boton volver.
        Button(
            onClick = {
                // Volver a la pagina anterior
                controladorNav.navigate("Login")
            }
        ) { Text( text = "Volver" ) }
    }
}