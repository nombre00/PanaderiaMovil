package com.example.panaderia.model

data class Envio (
    val id: Int,
    val idCliente: Int,
    val direccion: String,
    val estado: String,
    val productos: List<Producto>

)