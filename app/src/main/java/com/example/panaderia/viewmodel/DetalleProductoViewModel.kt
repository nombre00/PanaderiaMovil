package com.example.panaderia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.leerDetalleProducto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class DetalleProductoViewModel : ViewModel() {

    val _detalleProducto = MutableStateFlow<Producto>(Producto(0,"",0,"","",""))
    val detalleProducto: StateFlow<Producto> = _detalleProducto.asStateFlow()


    open fun cargarDetalleProducto(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerDetalleProducto(contexto).collect { producto ->
                _detalleProducto.value = producto
            }
        }
    }
}