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
import com.example.panaderia.model.Cliente
import com.example.panaderia.repository.leerCarritos
import kotlinx.coroutines.flow.first
import com.example.panaderia.model.Envio
import com.example.panaderia.model.Respuesta
import com.example.panaderia.repository.guardarEnvio
import com.example.panaderia.repository.leerClienteIngresado
import com.example.panaderia.repository.leerEnvios
import com.example.panaderia.repository.leerRespuesta


// ViewModel maneja la logica de los estados, a eso se refieren cuando dicen que es el interlocutor entre la UI y Model.

class CarritoViewModel : ViewModel() {

    // Primero necesitamos el estado de los envios.
    private val _envios = MutableStateFlow<List<Envio>>(emptyList())
    val envio: StateFlow<List<Envio>> = _envios.asStateFlow()
    // Buscamos los carritos, estamos repitiendo lo de arriba con una lista en vez de un carrito para no desarmar otras funcionalidades.
    private val _carritos = MutableStateFlow<List<Carrito>>(emptyList())
    val carritos: StateFlow<List<Carrito>> = _carritos.asStateFlow()
    // cliente ingresado
    private val _clienteIngresado = MutableStateFlow<Cliente>(Cliente("","","","","","",emptyList()))
    val clienteIngresado: StateFlow<Cliente> = _clienteIngresado.asStateFlow()

    // Una trampa
    private val _refresco = MutableStateFlow<Respuesta>(Respuesta(false))
    val refresco: StateFlow<Respuesta> = _refresco




    fun cargarRefresco(contexto: Context){
        viewModelScope.launch {
            leerRespuesta(contexto).collect { resfresco ->
                _refresco.value = resfresco
            }
        }
    }
    // Cargar todos los carritos.
    fun cargarCarritos(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerCarritos(contexto).collect { carritos ->
                _carritos.value = carritos
            }
        }
    }
    // Filtrar el carrito del cliente.
    fun filtrarCarritoCliente(idCliente: String): Carrito{
        val carritoCliente: Carrito = _carritos.value.find { it.idCliente == idCliente } ?: Carrito("0","0" ,mutableListOf<Producto>())
        return carritoCliente
    }




    // Eliminamos un producto del carrito.
    fun eliminarProductoCarrito(contexto: Context, productoID: String, idCarrito: String){
        // Corrutina
        viewModelScope.launch {
            // No se actualiza reactivamente
            // Copiamos la lista, recuerda que todas las copias son punteros a los originales.
            val carritosActualziados = _carritos.value.toMutableList()
            // Buscamos el indice del carrito en la lista.
            val indice = carritosActualziados.indexOfFirst { it.id == idCarrito }
            // Copiamos el carrito que vamos a editar.
            val carritoEncontrado = carritosActualziados[indice]
            // Copiamos los productos en el carrito.
            val productos = carritoEncontrado.productos.toMutableList() // Acá hay que cambiar para gatillar la cosa
            // Buscamos el indice del producto a borrar.
            val indiceProducto = productos.indexOfFirst { it.id == productoID }
            // Borramos.
            productos.removeAt(indiceProducto)

            // Creamos una copia del carrito ya editado, apuntamos a otro espacio de memoria.
            val carritoEditado = carritoEncontrado.copy(productos = productos)
            // Creamos una nueva lista en un espacio de memoria distinto.
            carritosActualziados[indice] = carritoEditado

            // Persistimos los cambios.
            guardarCarrito(contexto, carritosActualziados)
            // Actualizamos el estado.
            _carritos.value = carritosActualziados
        }
    }




    // Funcionalidad de comprar, afecta el carrito vaciandolo y genera un envio relacionado con el cliente.
    // Funcion que carga todos los envios del local storage.
    fun cargarEnvios(contexto: Context){
        // Corrutina.
        viewModelScope.launch {
            leerEnvios(contexto).collect { envios ->
                _envios.value = envios
            }
        }
    }
    // Funcion que crea un nuevo envio, lo agrega al estado que contiene los envios y persiste en local storage.
    fun comprar(contexto: Context, carritoCompra: Carrito){
        // Acciones que tenemos que hacer.
        // Tomamos los datos del carrito: idCliente, productos
        val idCliente = carritoCompra.idCliente
        val productosCompra = carritoCompra.productos

        // Tomamos los datos del cliente: direccion // Por ahora vamos a hardcodear este dato.
        val direccionCompra = "123 calle valparaiso"

        // Creamos un nuevo envio.
        // Por ahora hardcodeamos el id, despues vamos a tener que buscar el id más alto, sumarle 1 y pasarlo a string.
        val envioCompra = Envio("1", idCliente, direccionCompra, "preparando", productosCompra)


        // Guardamos el envio en la base de datos.
        // En kotlin, igual que en java, cunado asignamos un valor de un objeto existente, no creamos una copia, creamos un puntero al objeto original, por eso podemos modificar la copia y guardar el original y se guarda el cambio.
        // Corrutina
        viewModelScope.launch {
            // Tomamos la lista como estado y la guardamos en una variable para manipularla, recuerda que la variabl es un puntero.
            val enviosActualizados = _envios.value.toMutableList()
            // Agregamos el envio al puntero.
            enviosActualizados.add(envioCompra)
            // Persistimos el cambio
            guardarEnvio(contexto, enviosActualizados)




            // Buscamos el estado que guarda los carritos, lo editamos y persistimos.
            // Tomamos la lista como estado y la guardamos en una variable para manipularla, recuerda que la variabl es un puntero.
            val carritosActualizados = _carritos.value.toMutableList() // Estamos creando una lista nueva en un nuevo espacio de memoria
            // Buscamos el carrito editado y lo guardamos en una variable, que es un puntero.
            val carritoEditado = carritosActualizados.find { it.id == carritoCompra.id } ?: Carrito("0","0", mutableListOf())
            // Editamos el puntero
            carritoEditado.productos.clear()
            // Como editamos los productos del carrito vamos a copiar eso en otro espacio de memoria
            val productosNuevos = carritoEditado.productos.toMutableList() // Acá estariamos gatillando la recombersion de la pagina
            // Creamos un carrito nuevo en un nuevo espacio de memoria.
            val carritoNuevo = carritoEditado.copy(productos = productosNuevos) // Acá tambien se supone que gatille
            // Borramos y reemplazamos el carrito.
            carritosActualizados.remove(carritoEditado)
            carritosActualizados.add(carritoNuevo)

            // Persistimos el cambio
            guardarCarrito(contexto, carritosActualizados)
            // Actualizamos el estado de los carritos.
            _carritos.value = carritosActualizados
            // Actualizamos el estado de envios.
            _envios.value = enviosActualizados

            // Trampa
            // Alternar la bandera para forzar recomposición
            _refresco.value = _refresco.value.copy(valor = !_refresco.value.valor)
        }
    }

    // Carga el cliente ingresado.
    fun cargarClienteIngresado(contexto: Context){
        // Corrutina
        viewModelScope.launch {
            leerClienteIngresado(contexto).collect { cliente ->
                _clienteIngresado.value = cliente
            }
        }
    }









}













