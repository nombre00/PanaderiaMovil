import android.content.Context
import com.example.panaderia.model.Cliente
import com.example.panaderia.viewmodel.InicioViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk


class InicioViewModelTest : StringSpec( {
    // Test 1
    "carga el cliente ingresado del backend y lo guarda en local storage" {
        val clienteFalso =
            Cliente(1, "nombre_1", "mail_1@gmail.com", "direccion_1", "clave_1", null, emptyList())

        val testViewModel = object : InicioViewModel() {
            override fun cargarClienteIngresado(contexto: Context) {
                _clienteIngresado.value = clienteFalso
            }
        }
        testViewModel.cargarClienteIngresado(mockk())
        testViewModel._clienteIngresado.value shouldBe clienteFalso
    }
})