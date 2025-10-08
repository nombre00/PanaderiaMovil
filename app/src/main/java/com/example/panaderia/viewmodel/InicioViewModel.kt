package com.example.panaderia.viewmodel

import androidx.lifecycle.ViewModel  // Clase de la libreria de jetpack, maneja el ciclo de vida.
import androidx.lifecycle.viewModelScope  // Una corrutina que se cancela cuando el viewmodel se destruye.
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
    // Estado que se expone a la UI
    private val _mensaje = MutableStateFlow("Panadería Dulce tradición!")
    val message: StateFlow<String> = _mensaje.asStateFlow()

    // Funcion para actualizar el mensaje (ejemplo)
    fun actualizarMensaje(mensajeNuevo: String){
        viewModelScope.launch {
            _mensaje.value = mensajeNuevo
        }
    }

}