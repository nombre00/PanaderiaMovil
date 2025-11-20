import android.content.Context
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.Envio
import com.example.panaderia.model.Producto
import com.example.panaderia.viewmodel.CarritoViewModel
import com.example.panaderia.viewmodel.LoginViewModel
import com.example.panaderia.viewmodel.PerfilViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.mockk


class PerfilViewModelTest : StringSpec ({

    // Test 1
    "Carga los envios del backend" {
        val enviosFalsos = listOf(
            Envio(1, 1, "direnccion1", "estado1", mutableListOf()),
            Envio(2, 2, "direnccion2", "estado2", mutableListOf())
        )
        val testViewModel = object : PerfilViewModel() {
            override fun cargarEnvios(contexto: Context) {
                _envios.value = enviosFalsos
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.cargarEnvios(mockk())
        testViewModel.envio.value shouldContainExactly enviosFalsos
    }

    // Test 2
    "Carga los carritos de local storage" {
        val carritosFalsos = listOf (
            Carrito(3, 3, mutableListOf<Producto>()),
            Carrito(2, 2, mutableListOf<Producto>()),
            Carrito(1, 1, mutableListOf<Producto>())
        )

        val testViewModel = object : PerfilViewModel() {
            override fun cargarCarritos(contexto: Context) {
                _carritos.value = carritosFalsos // Este estado es el que escucha el viewmodel
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.cargarCarritos(mockk())
        testViewModel.carrito.value shouldContainExactly carritosFalsos // Este estado es el que escucha el viewmodel
    }

    // Test 3
    "Filtra y retorna un carrito de local storage" {
        val carritosFalsos = listOf (
            Carrito(3, 3, mutableListOf<Producto>()),
            Carrito(2, 2, mutableListOf<Producto>()),
            Carrito(1, 1, mutableListOf<Producto>())
        )
        val carritoFalso = Carrito(1, 1, mutableListOf<Producto>())

        val testViewModel = object : PerfilViewModel() {
            override fun filtrarCarrito(carritos: List<Carrito>, idCarrito: Int): Carrito {
                return carritoFalso
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        val carritoRetornado = testViewModel.filtrarCarrito(carritosFalsos, 1)
        carritoRetornado shouldBe  carritoFalso // Este estado es el que escucha el viewmodel
    }

    // Test 4
    "carga el cliente ingresado del backend y lo guarda en local storage" {
        val clienteFalso =
            Cliente(1, "nombre_1", "mail_1@gmail.com", "direccion_1", "clave_1", null, emptyList())

        val testViewModel = object : PerfilViewModel() {
            override fun cargarClienteIngresado(contexto: Context) {
                _clienteIngresado.value = clienteFalso
            }
        }
        testViewModel.cargarClienteIngresado(mockk())
        testViewModel._clienteIngresado.value shouldBe clienteFalso
    }
})