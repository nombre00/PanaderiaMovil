package com.example.panaderia.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// Este archivo sirve para trabajar el JSON que va a guardar y leer los carritos del local storage,

// Serializar lista de Carrito, retorna un JSON como string.
fun SerializarCarrito(carrito: Carrito): String{
    return Gson().toJson(carrito)
}

// Desserializar Carritos.
fun DesserializarCarritos(json: String): List<Carrito>{
    val type = object : TypeToken<List<Carrito>>() {}.type
    return Gson().fromJson(json, type)
}