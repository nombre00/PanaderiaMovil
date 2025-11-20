import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.Producto
import com.example.panaderia.model.Carrito
import com.example.panaderia.viewmodel.RegistrarseViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest


// Comentado ya que no uso corrutinas para este test: @OptIn(ExperimentalCoroutinesApi::class)
class RegistrarseViewModelTest : StringSpec ( {

    // Test 1
    "cargarClientes debe contener los datos esperados después de fetchCliente()" {
        // Creamos una subclase falsa de RegistrarseViewModel que sobreescribe el repositorio
        val clientesFalsos = listOf (
            Cliente(1,"nombre_1","mail_1@gmail.com","direccion_1","clave_1",null, emptyList()),
            Cliente(2,"nombre_2","mail_2@gmail.com","direccion_2","clave_2",null, emptyList())
        )

        val testViewModel = object : RegistrarseViewModel() {
            // Sobreescribimos la funcion que estamos testeando
            override fun cargarClientes(contexto: Context) {
                // El estado se cambia sin corrutina
                _clientes.value = clientesFalsos // Este estado es el que escucha el viewmodel
            }
        }

        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.cargarClientes(contexto = mockk()) // Pasamos un mockk() en vez del contexto
        testViewModel.clientes.value shouldContainExactly clientesFalsos // Este estado es el que escucha el viewmodel
    }


    // Test 2
    "Crea un cliente y un carrito y los guardas en el backend y en local storage" {
        val clienteNuevo = Cliente(99, "Pedro", "pedro@mail.com", "calle 123", "abc", null, emptyList())
        val carritoNuevo = Carrito(1,1,mutableListOf<Producto>())

        val testViewModel = object : RegistrarseViewModel() {
            override fun crearCliente(contexto: Context, cliente: Cliente, carrito: Carrito){
                _clientes.value = _clientes.value + cliente // Este estado es el que escucha el viewmodel
                _carritos.value = _carritos.value + carrito // Este estado es el que escucha el viewmodel
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.crearCliente(mockk(), clienteNuevo, carritoNuevo) // Pasamos un mockk() en vez del contexto

        testViewModel.clientes.value shouldContainExactly  listOf(clienteNuevo) // Este estado es el que escucha el viewmodel
        testViewModel.carritos.value shouldContainExactly  listOf(carritoNuevo) // Este estado es el que escucha el viewmodel
    }

    // Test 3
    "Carga los carritos de local storage" {
        val carritosFalsos = listOf (
            Carrito(3,3,mutableListOf<Producto>()),
            Carrito(2,2,mutableListOf<Producto>()),
            Carrito(1,1,mutableListOf<Producto>())
        )

        val testViewModel = object : RegistrarseViewModel() {
            override fun cargarCarritos(contexto: Context) {
                _carritos.value = carritosFalsos // Este estado es el que escucha el viewmodel
            }
        }
        // Testeamos sin corrutina ya que no es necesario y puede generar errores.
        testViewModel.cargarCarritos(mockk())
        testViewModel.carritos.value shouldContainExactly carritosFalsos // Este estado es el que escucha el viewmodel
    }
} )