package com.example.panaderia.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.repository.guardarClienteIngresado
import com.example.panaderia.repository.leerClienteIngresado
import com.example.panaderia.repository.leerClientes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    // Primero necesitamos el estado que recupera los clientes
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()
    // Luego necesitamos el estado que recupera el cliente ingresado.
    private val _clienteIngresado = MutableStateFlow<Cliente>(Cliente("","","","","","",emptyList()))
    val clienteIngresado: StateFlow<Cliente> = _clienteIngresado.asStateFlow()



    // Como cada cliente tiene un carrito tambien vamos a necesitar los carritos.
    private val _carritos = MutableStateFlow<List<Carrito>>(emptyList())
    val carritos: StateFlow<List<Carrito>> = _carritos.asStateFlow()


    // Carga los clientes.
    fun cargarClientes(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerClientes(contexto).collect { clientes ->
                _clientes.value = clientes
            }
        }
    }
    // Carga el cliente ingresado.
    fun cargarClienteIngresado(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerClienteIngresado(contexto).collect { cliente ->
                _clienteIngresado.value = cliente
            }
        }
    }


    // Funcion que genera el login.
    fun loginIngresar(contexto: Context, mail: String, clave: String): Boolean{
        // Carga los clientes, compara el mail y la contraseña y si encuentra el cliente gaurda el cliente en localStorage
        // Buscamos el cliente en los clientes
        val clienteIngresado = _clientes.value.find { it.clave == clave && it.mail == mail } ?: null

        if (clienteIngresado != null){
            // Si encontramos el cliente ingresado lo guardamos en localStorage como cliente ingresado
            if (clienteIngresado.clave == clave){
                _clienteIngresado.value = clienteIngresado

                // Guardamos en el dataStore.
                // Corrutina
                viewModelScope.launch {
                    guardarClienteIngresado(contexto, clienteIngresado)
                }
                return true // retornamos una bandera para luego hacer un toast
            } else {
                return false // retornamos una bandera para luego hacer un toast
            }
        }
        return false // retornamos una bandera para luego hacer un toast
    }
}

