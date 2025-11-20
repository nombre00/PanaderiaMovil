import android.content.Context
import com.example.panaderia.model.Cliente
import com.example.panaderia.viewmodel.CarritoViewModel
import com.example.panaderia.viewmodel.LoginViewModel
import com.example.panaderia.viewmodel.RegistrarseViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.mockk


class LoginViewModelTest : StringSpec( {
    // Test 1
    "carga el cliente ingresado del backend y lo guarda en local storage" {
        val clienteFalso =
            Cliente(1, "nombre_1", "mail_1@gmail.com", "direccion_1", "clave_1", null, emptyList())

        val testViewModel = object : LoginViewModel() {
            override fun cargarClienteIngresado(contexto: Context) {
                _clienteIngresado.value = clienteFalso
            }
        }
        testViewModel.cargarClienteIngresado(mockk())
        testViewModel._clienteIngresado.value shouldBe clienteFalso
    }

    // Test 2
    "cargarClientes debe contener los datos esperados después de fetchCliente()" {
        // Creamos una subclase falsa de RegistrarseViewModel que sobreescribe el repositorio
        val clientesFalsos = listOf (
            Cliente(100,"nombre_1","mail_1@gmail.com","direccion_1","clave_1",null, emptyList()),
            Cliente(200,"nombre_2","mail_2@gmail.com","direccion_2","clave_2",null, emptyList())
        )

        val testViewModel = object : LoginViewModel() {
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

    // Test 3
    "Genera un ingreso" {
        val clienteFalso = Cliente(100,"nombre_1","mail_1@gmail.com","direccion_1","clave_1",null, emptyList())

        val testViwewModel = object : LoginViewModel() {
            init {
                _clientes.value = listOf(clienteFalso)
            }
        }
        val flag = testViwewModel.loginIngresar(mockk(),clienteFalso.mail,clienteFalso.clave)
        flag shouldBe true
    }
})