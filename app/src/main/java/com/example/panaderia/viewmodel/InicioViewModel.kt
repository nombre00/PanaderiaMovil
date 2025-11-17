package com.example.panaderia.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel  // Clase de la libreria de jetpack, maneja el ciclo de vida.
import androidx.lifecycle.viewModelScope  // Una corrutina que se cancela cuando el viewmodel se destruye.
import com.example.panaderia.model.Cliente
import com.example.panaderia.repository.leerClienteIngresado
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow  // Un flujo de estado modificable.
import kotlinx.coroutines.flow.StateFlow  // Una version inmutable que exponemos a la UI para que reaccione a cambios.
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Que hace un viewmodel en una arquitectura MVVM
// Gestionar el estado de la pantalla de inicio: Almacena datos o información que la interfaz de usuario necesita mostrar o manipular.

// Contener la lógica de presentación: Maneja las acciones o cálculos que no pertenecen a la UI, como
// actualizar el texto, responder a eventos (como clics), o coordinar con un repositorio de datos.

// Ser independiente de la Vista: Esto significa que la UI (en Inicio.kt) solo se preocupa por mostrar
// lo que el ViewModel le proporciona, y el ViewModel no sabe cómo se ve la UI.

class InicioViewModel : ViewModel() {

    // Funcionalidad del cliente ingresado
    // cliente ingresado
    private val _clienteIngresado = MutableStateFlow<Cliente>(Cliente(0,"","","","",null,emptyList()))
    val clienteIngresado: StateFlow<Cliente> = _clienteIngresado.asStateFlow()
    // Carga el cliente ingresado.
    fun cargarClienteIngresado(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerClienteIngresado(contexto).collect { cliente ->
                _clienteIngresado.value = cliente
            }
        }
    }



    fun clickOfertas(clienteIngresado: Cliente, contexto: Context): Boolean{
        // Revisa si el usuario está ingresado y si si lo lleva a catalogo a comprar, sino gatilla un toast con un mensaje
        if (clienteIngresado.id == 0){
            //Toast.makeText(contexto, "Ingrese sesion", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

}