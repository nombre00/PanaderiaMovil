package com.example.panaderia.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// Este archivo sirve para trabajar el JSON que va a guardar y leer los carritos del local storage,

// Serializar lista de Carrito, retorna un JSON como string.
fun serializarRespuesta(respuesta: Respuesta): String{
    return Gson().toJson(respuesta)
}

// Desserializar Carritos.
fun desserializarRespuesta(json: String): Respuesta{
    val type = object : TypeToken<Respuesta>() {}.type
    return Gson().fromJson(json, type)
}