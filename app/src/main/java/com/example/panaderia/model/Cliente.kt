package com.example.panaderia.model

data class Cliente (
    // Datos personales.
    val id: String = "",
    val nombre: String = "",
    val mail: String = "",
    val direccion: String = "",
    val clave: String = "",

    // Datos para el sistema.
    val carritoId: String,
    val idEnvios: List<String>,
    val errores: ClienteErrores = ClienteErrores()
)

// Acá tienen que ir los valores erroneos
data class ClienteErrores(
    val id: String? = null,
    val nombre: String? = null,
    val mail: String? = null,
    val direccion: String? = null,
    val clave: String? = null
)