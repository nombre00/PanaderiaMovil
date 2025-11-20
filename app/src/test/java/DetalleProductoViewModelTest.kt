import android.content.Context
import com.example.panaderia.model.Producto
import com.example.panaderia.viewmodel.DetalleProductoViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk


class DetalleProductoViewModelTest : StringSpec ( {

    // Test 1
    "Carga un producto del local storage" {
        val productoFalso = Producto(1,"nombre",1000,"url","categoria","reseña")

        val testViewModel = object : DetalleProductoViewModel() {
            override fun cargarDetalleProducto(contexto: Context) {
                _detalleProducto.value = productoFalso
            }
        }
        testViewModel.cargarDetalleProducto(mockk())
        testViewModel.detalleProducto.value shouldBe productoFalso
    }
})