package com.example.panaderia.model

data class Envio (
    val id: String,
    val idCliente: String,
    val direccion: String,
    val estado: String,
    val productos: List<Producto>

)