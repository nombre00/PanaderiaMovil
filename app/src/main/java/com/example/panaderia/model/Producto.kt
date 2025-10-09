package com.example.panaderia.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity
data class Producto(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val nombre: String,
    @ColumnInfo
    val precio: Int,
    @ColumnInfo
    val url: String
)
