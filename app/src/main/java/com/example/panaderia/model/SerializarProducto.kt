package com.example.panaderia.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Este codigo sirve para trabajar el JSON que va a guardar los productos en local storage.


// Serializar lista de productos, retorna un JSON como string.
fun serializarProductos(productos: List<Producto>): String{
    return Gson().toJson(productos)
}
// Serializar JSON a lista de productos.
fun deserializarProductos(json: String): List<Producto> {
    val type = object : TypeToken<List<Producto>>() {}.type
    return Gson().fromJson(json, type)
}

// Funciones que serializan el producto de detalleProducto
// Serializar un producto a json
fun serializarDetalleProducto(producto: Producto): String{
    return Gson().toJson(producto)
}
// Deserializar JSON a un producto.
fun deserializarDetalleProducto(json: String): Producto {
    val type = object : TypeToken<Producto>() {}.type
    return Gson().fromJson(json, type)
}