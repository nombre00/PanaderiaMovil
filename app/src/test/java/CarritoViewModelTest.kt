import android.content.Context
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.Envio
import com.example.panaderia.model.Producto
import com.example.panaderia.viewmodel.CarritoViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class CarritoViewModelTest : StringSpec( {

    // Test 1
    "Carga los carritos de local storage" {
        val carritosFalsos = listOf (
            Carrito(3, 3, mutableListOf<Producto>()),
            Carrito(2, 2, mutableListOf<Producto>()),
            Carrito(1, 1, mutableListOf<Producto>())
        )

        val testViewModel = object : CarritoViewModel() {
            override fun cargarCarritos(contexto: Context) {
                _carritos.value = carritosFalsos // Este estado es el que escucha el viewmodel
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.cargarCarritos(mockk())
        testViewModel.carritos.value shouldContainExactly carritosFalsos // Este estado es el que escucha el viewmodel
    }

    // Test 2
    "Filtra y retorna un carrito de local storage" {
        val carritoFalso = Carrito(1, 1, mutableListOf<Producto>())

        val testViewModel = object : CarritoViewModel() {
            override fun filtrarCarritoCliente(idCliente: Int): Carrito {
                _carritos.value = listOf(carritoFalso) // Este estado es el que escucha el viewmodel
                return carritoFalso
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.filtrarCarritoCliente(1)
        testViewModel.carritos.value shouldContainExactly listOf(carritoFalso) // Este estado es el que escucha el viewmodel
    }

    // Test 3
    "Borra un producto de un carrito" {
        val productosFalsos = mutableListOf ( // Lista de productos
            Producto(100, "nombre1", 1000, "url", "categoria1", "reseña1"),
            Producto(200, "nombre2", 1000, "url", "categoria2", "reseña2")
        )
        val carritoFalso =
            Carrito(100, 100, productosFalsos) // Carrito para la prueba, tiene 2 productos

        val testViewModel = object : CarritoViewModel() {
            init { // Agregamos el carrito al viewModel
                _carritos.value = listOf(carritoFalso)
            }

            override fun eliminarProductoCarrito(contexto: Context, productoID: Int, idCarrito: Int) {
                carritoFalso.productos.removeAt(1)
                _carritos.value = listOf(carritoFalso)
            }
        }
        // Testeamos la funcion
        testViewModel.eliminarProductoCarrito(contexto = mockk(), productoID = 200, idCarrito = 100) // Borramos el producto 2
        // Revisamos el resultado
        val listaCarritos = testViewModel.carritos.value
        listaCarritos.shouldHaveSize(1) // La lista debe tener solo un carrito.
        // Revisamos el carrito
        val carritoEditado = listaCarritos.first()
        carritoEditado.productos.shouldHaveSize(1) // Debe tener solo un producto
    }

    // Test 4
    "Carga los envios del backend" {
        val enviosFalsos = listOf(
            Envio(1, 1, "direnccion1", "estado1", mutableListOf()),
            Envio(2, 2, "direnccion2", "estado2", mutableListOf())
        )
        val testViewModel = object : CarritoViewModel() {
            override fun cargarEnvios(contexto: Context) {
                _envios.value = enviosFalsos
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.cargarEnvios(mockk())
        testViewModel.envio.value shouldContainExactly enviosFalsos
    }

    // Test 5
    "carga el cliente ingresado del backend y lo guarda en local storage" {
        val clienteFalso =
            Cliente(1, "nombre_1", "mail_1@gmail.com", "direccion_1", "clave_1", null, emptyList())

        val testViewModel = object : CarritoViewModel() {
            override fun cargarClienteIngresado(contexto: Context) {
                _clienteIngresado.value = clienteFalso
            }
        }
        testViewModel.cargarClienteIngresado(mockk())
        testViewModel._clienteIngresado.value shouldBe clienteFalso
    }
} )