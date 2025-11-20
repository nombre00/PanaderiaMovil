/**

import android.content.Context
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.remote.ApiService
import com.example.panaderia.repository.getClientes
import com.google.android.gms.common.api.Response
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow



// Subclase
class TesteableClienteRepository (private val testApi: ApiService) : getCliente() {

    override suspend fun getClientes(): Response<List<Cliente>> {
        return testApi.getClientes()
    }
}
class ClienteRepositoryTest : StringSpec( {
    "getClientes() debe retornar una lista de clientes simulada" {
        // Simulamos los datos.
        val clientesFalsos = listOf (
            Cliente(1,"nombre_1","mail_1@gmail.com","direccion_1","clave_1",null, emptyList()),
            Cliente(2,"nombre_2","mail_2@gmail.com","direccion_2","clave_2",null, emptyList())
        )
        // Creamos un mock del apiService
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getClientes() } returns clientesFalsos
        // Usamos la clase de test inyectando el mock
        val repo = TesteableClienteRepository(mockApi)
        // ejecutamos el test
        val resultado = repo.getClientes()
        resultado shouldContainExactly clientesFalsos

    }
} )

 */