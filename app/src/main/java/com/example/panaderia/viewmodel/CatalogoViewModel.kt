package com.example.panaderia.viewmodel


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.Producto
import com.example.panaderia.remote.RetrofitInstance
import com.example.panaderia.repository.guardarCarrito
import com.example.panaderia.repository.guardarDetalleProducto
import com.example.panaderia.repository.leerCarritos
import com.example.panaderia.repository.leerCatalogoLS
import com.example.panaderia.repository.leerClienteIngresado
import com.example.panaderia.repository.leerDetalleProducto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogoViewModel: ViewModel() {


    // Necesitamos manejar estados del catalogo, el carrito y el usuario, falta el usuario.
    // Estado del catalogo.
    private val _catalogo = MutableStateFlow<List<Producto>>(emptyList())
    val catalogo: StateFlow<List<Producto>> = _catalogo.asStateFlow()

    // Estado del carrito.
    private val _carritos = MutableStateFlow<List<Carrito>>(emptyList())
    val carrito: StateFlow<List<Carrito>> = _carritos.asStateFlow()

    // Estado de la categoria seleccionada.
    private val _categoriaProducto = MutableStateFlow<String?>(null)
    val categoriaProducto: StateFlow<String?> = _categoriaProducto.asStateFlow()

    // Estado de los productos filtrados.
    private val _productosFiltrados = MutableStateFlow<List<Producto>>(emptyList())
    val productosFiltrados: StateFlow<List<Producto>> = _productosFiltrados.asStateFlow()

    private val _clienteIngresado =
        MutableStateFlow<Cliente>(Cliente(0, "", "", "", "", null, emptyList()))
    val clienteIngresado: StateFlow<Cliente> = _clienteIngresado.asStateFlow()


    // Funcion que carga el catalogo del local storage y lo retorna (se lo pasamos al componente listaCatalogo)
    fun cargarCatalogo(contexto: Context) {
        // Version local storage
        /**
        // Corrutina.
        viewModelScope.launch {
            leerCatalogoLS(contexto).collect { productos ->
                _catalogo.value = productos
            }
        }
        */

        // Version api rest.
        viewModelScope.launch {  // corrutina
            try {
                val respuesta = RetrofitInstance.api.getProductos()
                Log.d("API", "Respuesta recibida. Código: ${respuesta.code()}")

                if (respuesta.isSuccessful) { // Si la respuesta es exitosa
                    val productos = respuesta.body() ?: emptyList() // Guardamos los productos o una lista vacia en una variable
                    Log.d("API", "Productos recibidos: ${productos.size}")
                    _catalogo.value = productos // le pasamos los productos al estado que escuchamos.

                    // Guardamos en local storage por si acaso, esto nos permite acceder a los datos sin internet
                    // Aun no hecho
                }
            } catch(e: Exception){
                // Si hay un error cargamos los datos locales
                Log.e("API", "Error cargando productos", e)
            }
        }

    }

    // Funcion que carga todos los carritos del local storage
    fun cargarCarritos(contexto: Context) {
        // Version local storage.
        // Corrutina
        viewModelScope.launch {
            leerCarritos(contexto).collect { carritos ->
                _carritos.value = carritos
            }
        }

        // Version api rest.
        /**
        viewModelScope.launch {
            try {
                val respuesta = RetrofitInstance.api.getCarritos() // Buscamos los datos por rest
                if (respuesta.isSuccessful){ // Si la respuesta es 200
                    val carritos = respuesta.body() ?: emptyList() // Pasamos los datos a una variable
                    _carritos.value = carritos // pasamos los datos a el estado escuchado.
                }
            }catch(e: Exception){
                Log.e("API", "Error cargando carritos", e)
            }
        }
        */
    }

    // Funcion que agrega un producto al carrito.
    fun agregarProductoAlCarrito(contexto: Context, producto: Producto, idCarrito: Int) {

        // En kotlin, igual que en java, cunado asignamos un valor de un objeto existente, no creamos una copia, creamos un puntero al objeto original, por eso podemos modificar la copia y guardar el original y se guarda el cambio.

        // Version local storage.
        // Corrutina.
        viewModelScope.launch {
            // Buscamos los carritos del estado y los guardamos en una variable
            val carritosActualizados = _carritos.value.toMutableList()
            // Buscamos el carrito por su id.
            val carritoUsuario = carritosActualizados.find { it.id == idCarrito }

            // Si encontramos el carrito.
            if (carritoUsuario != null) {
                // Agregamos el producto al carrito.
                carritoUsuario.productos.add(producto) // Modificamos la copia, ambos apuntan al mismo espacio de memoria.
                // Persistimos el cambio en local storage.
                guardarCarrito(
                    contexto,
                    carritosActualizados
                ) // Guardamos el original, ambos apuntan al mismo espacio de memoria.
                Toast.makeText(contexto, "Producto agregado al carrito.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    contexto,
                    "Inicie sesión para agregar un producto.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Version api rest.
        /**
        viewModelScope.launch {
            try{
                // Buscamos el carrito por su id.
                val respuestaGet = RetrofitInstance.api.getCarritoPorId(idCarrito)
                val carritoActual = respuestaGet.body() ?: throw Exception("Carrito no encontrado (body nulo)")
                if (respuestaGet.isSuccessful){
                    // Creamos una copia en otro espacio de memoria y modificamos eso.
                    val carritoModificado = carritoActual.copy()
                    // Agregamos el producto al carrito.
                    carritoModificado.productos.add(producto)
                    // Enviamos al backend.
                    val respuestaPut = RetrofitInstance.api.actualizarCarrito(idCarrito, carritoModificado)
                    // Revisamos que el put fue exitoso.
                    if (respuestaPut.isSuccessful){
                        val carritoActualizado = respuestaPut.body() ?: throw Exception("Respuesta vacía tras actualizar")
                        // Actualizamos el estado local
                        val carritosActuales = _carritos.value.toMutableList()
                        val indice = carritosActuales.indexOfFirst { it.id == idCarrito }
                        if (indice != -1) {
                            carritosActuales[indice] = carritoActualizado
                            _carritos.value = carritosActuales
                        }
                    }
                }
            }catch(e: Exception){
                Log.e("API", "Error agregando el producto", e)
            }
        }
        */
    }


    // Funcion que cambia el estado de la categoria seleccionada.
    fun cambiarSeleccionProductos(categoria: String) {
        _categoriaProducto.value = categoria
        filtarProductos()
    }

    // Funcion que filtra los productos por categoria.
    fun filtarProductos() {
        _productosFiltrados.value =
            if (_categoriaProducto.value == "") { // Si la categoria seleccionada es nula
                _catalogo.value // mostramos todos cuando no se selecciona una categoria
            } else {
                _catalogo.value.filter { it.categoria == _categoriaProducto.value } // Sino filtramos por categoria.
            }
    }


    // Carga el cliente ingresado.
    fun cargarClienteIngresado(contexto: Context) {
        // Corrutina
        viewModelScope.launch {
            leerClienteIngresado(contexto).collect { cliente ->
                _clienteIngresado.value = cliente
            }
        }
    }


    private val _detalleProducto = MutableStateFlow<Producto>(Producto(0, "", 0, "", "", ""))
    val detalleProducto: StateFlow<Producto> = _detalleProducto.asStateFlow()


    fun cargarDetalleProducto(contexto: Context) {
        // Corrutina
        viewModelScope.launch {
            leerDetalleProducto(contexto).collect { producto ->
                _detalleProducto.value = producto
            }
        }
    }

    fun persistirDetalleProducto(contexto: Context, producto: Producto) {
        viewModelScope.launch {
            guardarDetalleProducto(contexto, producto)
        }
    }
}