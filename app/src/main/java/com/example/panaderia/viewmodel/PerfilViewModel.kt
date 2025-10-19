package com.example.panaderia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Envio
import com.example.panaderia.repository.leerCarritos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.leerEnvios

class PerfilViewModel : ViewModel() {

    // Necesitamos manejar los estados del usuario, el carrito, los envios.

    // Funcionalidad del carrito.
    // Estado del carrito.
    private val _carritos = MutableStateFlow<List<Carrito>>(emptyList())
    val carrito: StateFlow<List<Carrito>> = _carritos.asStateFlow()

    // Funcion que carga todos los carritos del local storage
    fun cargarCarritos(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerCarritos(contexto).collect { carritos ->
                _carritos.value = carritos
            }
        }
    }
    // Funcion que filtra los carritos y retorna el carrito del usuario.
    fun filtrarCarrito(carritos: List<Carrito>, idCarrito: String): Carrito {
        val carrito: Carrito = _carritos.value.find { it.id == idCarrito } ?: Carrito("0","0" ,mutableListOf<Producto>())
        return carrito
    }


    // Funcionalidad de los envios.
    private val _envios = MutableStateFlow<List<Envio>>(emptyList())
    val envio: StateFlow<List<Envio>> = _envios.asStateFlow()

    // Funcion que carga todos los envios del local storage.
    fun cargarEnvios(contexto: Context){
        // Corrutina.
        viewModelScope.launch {
            leerEnvios(contexto).collect { envios ->
                _envios.value = envios
            }
        }
    }
    // Funcion que filtra los envios y retorna el o los envios del usuario.
    fun filtrarEnvios(envios: List<Envio>, idCliente: String): List<Envio> {
        val envios: List<Envio> = _envios.value.filter { it.idCliente == idCliente } // ?: emptyList<Envio>()  - parece no ser necesario el lambda, lo comentamos.
        return envios
    }
}