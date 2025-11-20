package com.example.panaderia.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.remote.ApiService
import com.example.panaderia.remote.RetrofitInstance
import com.example.panaderia.repository.getClientes
import com.example.panaderia.repository.guardarCarrito
import com.example.panaderia.repository.guardarClientes
import com.example.panaderia.repository.leerCarritos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class RegistrarseViewModel : ViewModel() {

    // Primero necesitamos el estado que recupera los clientes
    val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()
    // Como cada cliente tiene un carrito tambien vamos a necesitar los carritos.
    val _carritos = MutableStateFlow<List<Carrito>>(emptyList())
    val carritos: StateFlow<List<Carrito>> = _carritos.asStateFlow()


    // Funciones suspendidas que cargan, escuchan, los cambios en los estados.
    // Carga los carritos.
    open fun cargarCarritos(contexto: Context){
        // Version local storage.
        // Corrutina
        viewModelScope.launch {
            leerCarritos(contexto).collect { carritos ->
            _carritos.value = carritos
            }
        }

        // Version api rest.
        /**
        viewModelScope.launch {
            try {
                val respuesta = RetrofitInstance.api.getCarritos() // Buscamos los datos por rest
                if (respuesta.isSuccessful){ // Si la respuesta es 200
                    val carritos = respuesta.body() ?: emptyList() // Pasamos los datos a una variable
                    _carritos.value = carritos // pasamos los datos a el estado escuchado.
                }
            }catch(e: Exception){
                Log.e("API", "Error cargando carritos", e)
            }
        }
        */
    }


    // Carga los usuarios.
    open fun cargarClientes(contexto: Context){
        // Version local storage
        /**
        // Corrutina
        viewModelScope.launch {
        leerClientes(contexto).collect { clientes ->
        _clientes.value = clientes
        }
        }
         */
        // Version api rest
        viewModelScope.launch {
            try{
                val respuestaGet = getClientes()
                if (respuestaGet.isSuccessful){
                    val clientes = respuestaGet.body() ?: emptyList()
                    _clientes.value = clientes
                }
            }catch(e: Exception){
                Log.e("API", "Error cargando clientes", e)
            }
        }
    }


    // Funciones CRUD.
    // Crear un nuevo cliente y guardarlo.
    open fun crearCliente(contexto: Context, cliente: Cliente, carrito: Carrito){
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
            // Guardamos en el backend
            val respuestaPost2 = RetrofitInstance.api.guardarCarrito(carrito.id, carrito)
            if (!respuestaPost2.isSuccessful){
                throw Exception("Error al crear el carrito: ${respuestaPost2.code()}")
            }
            // Guardamos en el backend
            val respuestaPost = RetrofitInstance.api.guardarCliente(cliente.id, cliente)
            if (!respuestaPost.isSuccessful){
                throw Exception("Error al crear el cliente: ${respuestaPost.code()}")
            }
        }
    }
}