package com.example.panaderia.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Funcion que recibe una lista de objetos y retorna un json
fun SerializarCliente (clientes: List<Cliente>): String {
    return Gson().toJson(clientes)
}

// Funcion que recibe un json y retorna una lista de objetos.
fun DesserializarCliente(json: String): List<Cliente> {
    val type = object : TypeToken<List<Cliente>>() {}.type
    return Gson().fromJson(json, type)
}



// Funciones que serializan el cliente ingresado
fun SerializarClienteIngresado(cliente: Cliente): String {
    return Gson().toJson(cliente)
}
fun DesserializarClienteIngresado(json: String): Cliente {
    val type = object : TypeToken<Cliente>() {}.type
    return Gson().fromJson(json, type)
}