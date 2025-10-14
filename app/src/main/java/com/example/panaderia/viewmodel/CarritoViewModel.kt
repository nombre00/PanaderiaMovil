package com.example.panaderia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.guardarCarrito
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.panaderia.repository.leerCarritos
import kotlinx.coroutines.flow.first


// ViewModel maneja la logica de los estados, a eso se refieren cuando dicen que es el interlocutor entre la UI y Model.

class CarritoViewModel : ViewModel() {

    // Estado activo para el carrito actual, guarda el carrito actual.
    // El estado es a lo que la UI reacciona.
    private val _carrito = MutableStateFlow<Carrito?>(null)
    val carrito: StateFlow<Carrito?> = _carrito.asStateFlow()

    // Cargar el carrito por su id y actualiza el estado.
    fun cargarCarrito(contexto: Context, carritoID: String){
        viewModelScope.launch{
            try {
                val carritos = leerCarritos(contexto).first()
                // Creamos una variable que guarda el carrito encontrado.
                val carritoEncontrado  = carritos.find { it.id == carritoID } // Comparamos las ids de los carritos
                // Si encontramos el carrito pasamos el valor como estado, sino retornamos un carrito vacio con id 0.
                _carrito.value = carritoEncontrado ?: Carrito("0", mutableListOf())
                Log.d("CarritoViewModel", "Carrito cargado: ${_carrito.value}")
            }catch (e: Exception){
                Log.e("CarritoViewModel", "Error al cargar carrito: ${e.message}")
            }
        }
    }

    // Eliminamos un producto del carrito.
    fun eliminarProductoCarrito(contexto: Context, productoID: String){
        viewModelScope.launch {
            // Tomamos el valor del carrito actual, que viene del estado del contexto.
            _carrito.value?.let { carritoActual ->
                // Creamos una variable que contiene los productos del carrito.
                val productos = carritoActual.productos.toMutableList().apply {
                    // Tomamos el primer indice encontrado.
                    val index = indexOfFirst { it.id == productoID }
                    if (index >= 0) { // Sino borramos todos los productos con ese indice.
                        removeAt(index) // Y luego borramos
                    }
                }
                // Creamos una variable que contiene el carrito editado.
                val carritoEditado = carritoActual.copy(productos = productos)
                // Le pasamos el carrito editado al estado.
                _carrito.value = carritoEditado

                // Guardamos el carrito actualizado en el data store.
                val carritosActuales = leerCarritos(contexto).first() // Usamos first() ontener la lista actual

                val carritosActualizados = carritosActuales.toMutableList().apply {
                    // Creamos una variable que contiene la lista de carritos actualizados.
                    val index = indexOfFirst { it.id == carritoEditado.id } // La condicion es encontrar el carrito editado
                    // indexOfFirst es una forma de iterar, retorna el indice del primer objeto que cumpla con la condicion
                    if (index >= 0) { // Si encontramos el carrito
                        set(index, carritoEditado) // Lo reemplazamos por el carrito editado.
                    }
                }
                guardarCarrito(contexto, carritosActualizados)  // Guardamos los cambios.
            }
        }
    }
}













