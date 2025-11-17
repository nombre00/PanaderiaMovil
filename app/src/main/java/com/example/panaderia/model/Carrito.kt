package com.example.panaderia.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo


@Entity
data class Carrito (
    @PrimaryKey
    val id: Int,
    val idCliente: Int,
    var productos: MutableList<Producto>
)

