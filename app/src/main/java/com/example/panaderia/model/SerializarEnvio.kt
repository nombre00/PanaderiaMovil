package com.example.panaderia.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// Convierte lista de objetos a json
fun SerializarEnvio(envios: List<Envio>): String {
    return Gson().toJson(envios)
}


// Convierte json a lista de objetos.
fun DesserializarEnvios(json: String): List<Envio> {
    val type = object : TypeToken<List<Envio>>() {}.type
    return Gson().fromJson(json, type)
}
