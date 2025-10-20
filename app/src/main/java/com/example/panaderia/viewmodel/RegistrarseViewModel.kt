package com.example.panaderia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.repository.guardarCarrito
import com.example.panaderia.repository.guardarClientes
import com.example.panaderia.repository.leerCarritos
import com.example.panaderia.repository.leerClientes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrarseViewModel : ViewModel() {

    // Primero necesitamos el estado que recupera los clientes
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()
    // Como cada cliente tiene un carrito tambien vamos a necesitar los carritos.
    private val _carritos = MutableStateFlow<List<Carrito>>(emptyList())
    val carritos: StateFlow<List<Carrito>> = _carritos.asStateFlow()


    // Funciones suspendidas que cargan, escuchan, los cambios en los estados.
    // Carga los carritos.
    fun cargarCarritos(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerCarritos(contexto).collect { carritos ->
                _carritos.value = carritos
            }
        }
    }
    // Carga los usuarios.
    fun cargarClientes(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerClientes(contexto).collect { clientes ->
                _clientes.value = clientes
            }
        }
    }


    // Funciones CRUD.
    // Crear un nuevo cliente y guardarlo.
    fun crearCliente(contexto: Context, cliente: Cliente, carrito: Carrito){
        // Asumimos que los datos ya fueron validados y el cliente creado corresponde.
        // Corrutina
        viewModelScope.launch {
            // Guardamos el cliente
            val clientesActualizado = _clientes.value.toMutableList() // Creamos una lista nueva en otro espacio de memoria.
            clientesActualizado.add(cliente) // Guardamos el cliente nuevo en la base de datos.
            guardarClientes(contexto, clientesActualizado) // persistimos los cambios

            // Guardamos el carrito del cliente
            val carritosActualizados = _carritos.value.toMutableList() // Creamos una lista nueva en otro espacio de memoria.
            carritosActualizados.add(carrito) // Guardamos el carrito nuevo en la base de datos.
            guardarCarrito(contexto, carritosActualizados) // persistimos los cambios
        }
    }

}