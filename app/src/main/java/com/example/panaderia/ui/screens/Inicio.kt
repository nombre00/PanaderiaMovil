package com.example.panaderia.ui.screens



import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panaderia.ui.theme.panaderia
import com.example.panaderia.viewmodel.InicioViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Producto
import com.example.panaderia.repository.guardarCarrito
import com.example.panaderia.repository.guardarCatalogo
import com.example.panaderia.ui.components.Carrusel
import com.example.panaderia.ui.components.CarruselCafe
import com.example.panaderia.ui.components.Footer
import com.example.panaderia.ui.components.ImagenFondo
import com.example.panaderia.ui.components.Nosotros
import com.example.panaderia.ui.components.PromocionDia
import com.example.panaderia.ui.components.Titulo
import com.example.panaderia.ui.theme.Azul2
import com.example.panaderia.ui.theme.Rojo1
import com.example.panaderia.ui.theme.Verde1
import kotlinx.coroutines.launch
import com.example.panaderia.R
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.Envio
import com.example.panaderia.repository.guardarClientes
import com.example.panaderia.repository.guardarEnvio

@Composable
fun Inicio(viewModel: InicioViewModel = viewModel(), controladorNav: NavHostController) {


    panaderia {
        InicioScaffold(viewModel, controladorNav)
    }
}

// Funcion que crea el scaffold.
@Composable
fun InicioScaffold(viewModel: InicioViewModel, controladorNav: NavHostController){

    // Vamos a escuchar si el cliente está ingresado para las reacciones de unos componentes.
    // Cargamos el cliente ingresado
    val clienteIngresado by viewModel.clienteIngresado.collectAsStateWithLifecycle()

    // Tomamos el contexto local
    val contexto = LocalContext.current

    // Revisamos el estado del cliente ingresado
    LaunchedEffect(Unit) {
        viewModel.cargarClienteIngresado(contexto)
    }

    Scaffold(
        // Caja que contiene la imagen de fondo.
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                // Imagen de fondo.
                ImagenFondo(
                    modifier = Modifier.padding(paddingValues)
                )
                // Columna para ordenar.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Contenido visual de la pagina.
                    // Que queremos mostrar.
                    // Productos: Un contenedor con imagenes animables.
                    // Ofertas:
                    // Fechas importantes:
                    // contenido que va encima de la imagen.
                    //Titulo(titulo = "Inicio")

                    Text(text = "Panes del mes",
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Verde1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Carrusel(viewModel, clienteIngresado, controladorNav)

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(onClick = { controladorNav.navigate("Login") }){ Text( text = "Login" ) }

                    Spacer(modifier = Modifier.height(40.dp))

                    PromocionDia(viewModel, clienteIngresado, controladorNav)

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(text = "Cafes de la casa",
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Verde1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CarruselCafe(viewModel, clienteIngresado, controladorNav)

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(text = "Nosotros",
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Verde1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Nosotros()




                    // Empuja todo lo que viene después hacia abajo
                    Spacer(modifier = Modifier.weight(1f))
                    // Esto lo hacemos acá ya que para acceder a un contexto tenemos que hacerlo desde un componente @Composable, este es el primer componente que cargamos
                    // Lo primero que vamos a hacer es crear una lista de productos que vamos a guardar en local storage.
                    // Lista de productos.
                    // Por ahora creamos una lista de obejtos acá, despues vamos a referenciar local Storage.
                    val productos = mutableListOf<Producto>()
                    // Creamos y agregamos los productos a la lista.
                    // Tortas.
                    productos.add(Producto("kuchen_M", "Kuchen de manzana", 19000, "https://cdn0.recetasgratis.net/es/posts/2/9/2/kuchen_de_manzana_facil_y_rapido_45292_orig.jpg", "torta", "Un clásico de la cocina chilena-alemana que sabe a hogar. Manzanas frescas cortadas a mano, canela molida al momento, y una masa quebrada que se deshace en la boca. Horno lento para que el jugo de la fruta se caramelice naturalmente. Ideal para compartir con un té o café en la tarde."))
                    productos.add(Producto("mil_hoja", "Torta de Milhoja", 16000, "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg", "torta","Capas finas de hojaldre crujiente que se rompen con solo tocarlas, intercaladas con manjar casero cocinado lentamente y crema chantilly batida al punto justo. Cada bocado es una explosión de texturas: crujiente, cremoso, dulce. Un postre elegante pero sin pretensiones, perfecto para celebrar o simplemente darse un gusto."))
                    productos.add(Producto("strudel", "Strudel de manzana", 21000, "https://www.puffpastry.com/wp-content/uploads/2017/02/BH_7284.jpg", "torta", "Masa hojaldrada estirada a mano hasta quedar casi transparente, enrollada con manzanas asadas, pasas maceradas en ron, nueces tostadas y un toque de canela. Se sirve tibia, y si la acompañas con una bola de helado de vainilla, es una experiencia que roza lo celestial. Un viaje a los Alpes en cada cucharada."))
                    productos.add(Producto("t_3_leches", "Torta tres leches", 18000, "https://wattsindustrial.cl/wp-content/uploads/2023/03/tortatresleches-min.jpeg", "torta","Bizcocho esponjoso empapado en una mezcla perfecta de leche evaporada, condensada y crema, coronado con merengue italiano quemado con soplete. Húmeda sin ser empalagosa, dulce sin ser pesada. Es el postre que todos piden en cumpleaños, y con razón: cada porción es una caricia al alma."))
                    productos.add(Producto("p_limon", "Pie de limón", 18000, "https://images.unsplash.com/photo-1681329142517-6daaa56d3670?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", "torta","Base crujiente de galleta triturada con mantequilla, relleno de crema de limón natural con jugo recién exprimido y ralladura fresca. La acidez corta la dulzura, creando un equilibrio perfecto. Fresco, ligero, ideal para días de calor o como final refrescante después de una comida abundante."))
                    productos.add(Producto("kuchen_n", "Kuchen de nuez", 17000, "https://i0.wp.com/entreparrilleros.cl/wp-content/uploads/2024/02/Kuchen-de-nuez.png?fit=1080%2C1080&ssl=1", "torta","Masa quebrada con nueces tostadas en sartén para realzar su sabor, un toque de miel y azúcar morena que se carameliza en el horno. Textura rústica, sabor profundo, aroma que llena la cocina. Perfecto para acompañar un té negro o un café con leche en una tarde lluviosa."))
                    productos.add(Producto("s_negra", "Selva negra", 16000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkQeC-bVK7OKElOTJwbTTya0dxXdGm3iqBJA&s", "torta","Bizcocho de chocolate intenso, capas de crema batida, cerezas al licor Kirsch y virutas de chocolate negro. Un bosque oscuro de sabores: amargo, dulce, ácido, cremoso. Un postre con historia, elegante y misterioso, que nunca pasa de moda."))
                    productos.add(Producto("t_choco", "Torta de Chocolate", 15000, "https://amoradulce.com/wp-content/uploads/2019/12/Torta-chocolate-1_04_13_2024-scaled.jpg", "torta","Bizcocho húmedo de cacao 70%, bañado en ganache de chocolate semiamargo y decorado con cacao en polvo. Para los amantes del chocolate puro, sin distracciones. Cada bocado es denso, rico, adictivo. Ideal para cumpleaños o para cuando necesitas un abrazo comestible."))
                    productos.add(Producto("t_cafe", "Torta de Café", 15000, "https://www.littlesugarsnaps.com/wp-content/uploads/2021/05/Coffee-Flavoured-Cake-Featured-Image-8692.jpg", "torta","Capas de bizcocho infusionado con café expreso recién hecho, relleno de crema de mantequilla con café y un toque de licor. Aroma intenso, sabor equilibrado entre dulce y amargo. Perfecta para la merienda o para empezar el día con energía y placer."))
                    productos.add(Producto("c_volteada", "Crema Volteada", 14000, "https://dellepiane.pe/wp-content/uploads/2023/08/crema-volteada-001.jpg", "torta","Flan tradicional con caramelo dorado hecho a fuego lento, cremoso y tembloroso, con un toque de vainilla bourbon. Se sirve frío, directamente del molde. Un postre de abuela que evoca recuerdos de infancia, simple pero inolvidable."))
                    // Panes.
                    productos.add(Producto("marraqueta", "Marraqueta", 1700, "https://comerciante.lacuarta.com/wp-content/uploads/2022/05/Marraqueta-Tema-1-ok.jpg", "pan","La reina del pan chileno. Corteza dorada y crujiente que se rompe al partirla, miga esponjosa y alveolada. Recién salida del horno todas las mañanas. Perfecta con palta, tomate, queso o simplemente con mantequilla derretida. Un imprescindible en cualquier mesa."))
                    productos.add(Producto("panhuevo", "Pan de huevo", 2400, "https://www.tipicochileno.cl/wp-content/uploads/2021/05/pan-de-huevo-1200-628.jpg", "pan","Pan dulce con aroma a vainilla y huevo fresco, miga suave y ligeramente húmeda. Ideal para el té de la tarde, para untar con mermelada o para hacer tostadas francesas. Un pan que sabe a celebración y a hogar."))
                    productos.add(Producto("dobladita", "Dobladita de queso", 1900, "https://tiendaelchileno.com/wp-content/uploads/2024/12/dobladita-pagina-web.jpeg", "pan","Hojaldre crujiente relleno de queso fundido que se derrite al morderlo. Caliente, dorada, con capas que se desprenden fácilmente. Perfecta para el desayuno o como snack rápido. Un clásico de panadería que nunca falla."))
                    productos.add(Producto("medialuna", "Medialuna", 4600, "https://cocinerosargentinos.com/content/recipes/original/recipes.12138.jpeg", "pan","Croissant chileno con mantequilla de verdad, hojaldrado perfecto, crujiente por fuera y suave por dentro. Se sirve tibia, ideal con mermelada de frambuesa o simplemente sola. Un lujo cotidiano que eleva cualquier desayuno."))
                    productos.add(Producto("hallulla","Hallulla",1600,"https://tofuu.getjusto.com/orioneat-local/resized2/XSCcrg3DdfEDenF9o-2400-x.webp","pan","Pan plano tradicional, ligeramente dulce, con miga compacta y suave. Perfecta para el desayuno con palta machacada, tomate y aceite de oliva, o con queso fresco. Un pan humilde pero esencial en la mesa chilena."))
                    productos.add(Producto("baguette","Baguette",3000,"https://www.recetasdepan.net/wp-content/uploads/2019/05/Receta-de-pan-baguette.jpg","pan","Corteza crujiente y dorada, miga aireada con alveolos grandes. Hecha con harina de fuerza y fermentación lenta. Ideal para sándwiches, para mojar en sopas o para acompañar quesos y patés. Un pan con carácter."))
                    productos.add(Producto("bocadodedama","Bocado de dama",2100,"https://masapan.cl/cdn/shop/files/Bocado_de_dama_en_temuco_11zon.webp?v=1724895309","pan","Pequeño bollo dulce cubierto de azúcar glass, con miga esponjosa y sabor delicado a vainilla. Perfecto para la merienda, para acompañar un café o como detalle en una caja de regalo. Un capricho pequeño pero inolvidable."))
                    // Galletas
                    productos.add(Producto("corazones", "Galletas de corazon", 2000, "https://www.dagusto.com.co/wp-content/uploads/2020/11/galletas-imagen-cuerpo.jpg", "galleta","Galletas de mantequilla con forma de corazón, crujientes y con un toque de vainilla. Perfectas para regalar, para decorar una torta o para disfrutar con un té. Cada una lleva un mensaje de cariño horneado dentro."))
                    productos.add(Producto("g_chocolate", "Galletas chocolate", 2000, "https://cdn7.kiwilimon.com/recetaimagen/31079/640x640/35433.jpg.jpg", "galleta","Masa suave con chips de chocolate belga que se derriten en la boca. Crujientes por fuera, suaves por dentro. Ideales para mojar en leche, para la lonchera o para un antojo a media tarde. Un clásico que nunca falla."))
                    productos.add(Producto("magdalenas", "Magdalenas",2000,"https://www.onceuponachef.com/images/2022/10/madeleines-760x960.jpg","galleta","Pequeños bizcochitos esponjosos con forma de concha, sabor a limón y mantequilla. Tiernos, aromáticos, con una costra dorada. Perfectos para el té, para llevar o para un momento de pausa en el día."))
                    productos.add(Producto("palmeritas","Palmeritas",2000,"https://cdn0.uncomo.com/es/posts/7/0/8/como_hacer_palmeritas_39807_orig.jpg","galleta","Hojaldre caramelizado con azúcar, doblado en forma de palma. Crujiente, dulce, con capas que se desprenden al morder. Imposible comer solo una. Un capricho rápido que siempre alegra el día."))
                    productos.add(Producto("choco_blanco","Galletas choco blanco",2000,"https://farm5.staticflickr.com/4736/38190985724_7cfe37a26d_b.jpg","galleta","Chocolate blanco cremoso en una masa suave y crujiente. Dulzura delicada, textura perfecta. Ideal para quienes prefieren un chocolate menos intenso pero igual de adictivo. Un lujo en cada mordisco."))
                    // Cafes.
                    productos.add(Producto("late", "Café latte", 2100, "https://images.ctfassets.net/0e6jqcgsrcye/53teNK4AvvmFIkFLtEJSEx/4d3751dcad227c87b3cf6bda955b1649/Cafe_au_lait.jpg", "cafe","Espresso suave con leche vaporizada a 60°C y una capa de espuma cremosa. Suave, cálido, reconfortante. Perfecto para empezar el día con calma o para una pausa en la tarde. Un abrazo en taza."))
                    productos.add(Producto("americano", "Café americano", 1800, "https://excelso77.com/wp-content/uploads/2024/05/por-que-el-cafe-americano-se-llama-asi-te-lo-contamos.webp", "cafe", "Café negro intenso, filtrado con agua caliente a 90°C. Puro, fuerte, sin adornos. Ideal para quienes aman el sabor del grano tostado en su máxima expresión. Un clásico que despierta los sentidos."))
                    productos.add(Producto("moca", "Café mocaccino", 2600, "https://www.cabucoffee.com/newimages/Cafe-Moca.jpg", "cafe","Espresso, chocolate caliente, leche espumosa y un toque de cacao en polvo. Dulce, indulgente, cremoso. Un postre líquido que combina lo mejor del café y el chocolate. Perfecto para días fríos o para darse un gusto."))
                    productos.add(Producto("helado", "Café helado", 3200, "https://osterstatic.reciperm.com/webp/10230.webp", "cafe","Café frío con hielo, leche condensada y un toque de vainilla. Refrescante, dulce, con cuerpo. Ideal para tardes de calor o para un boost de energía sin calor. Se sirve con pajita y una sonrisa."))

                    // Creamos una variable para manipular el contexto local.
                    val contexto = LocalContext.current


                    // Creamos una lista de carritos.
                    val carritos = mutableListOf<Carrito>()
                    // Creamos y agregamos los carritos.
                    //carritos.add(Carrito(id = "1", idCliente = "1", productos = mutableListOf()))

                    // Creamos una lista de envios.
                    val envios = mutableListOf<Envio>()

                    // Creamos una lista de clientes.
                    val clientes = mutableListOf<Cliente>()


                    // Variable para manejar las corrutinas, llamo las funciones suspend con esto.
                    val coroutinesScope = rememberCoroutineScope()
                    // Guardamos los datos en local storage
                    Box(modifier = Modifier.size(height = 50.dp, width = 220.dp)){
                        Button(onClick = {
                            coroutinesScope.launch {
                                // Guardamos las listas en la base de datos.
                                guardarCatalogo(contexto, productos)
                                guardarCarrito(contexto, carritos)
                                guardarEnvio(contexto, envios)
                                guardarClientes(contexto, clientes)
                                Toast.makeText(contexto, "carritos ${carritos.size}", Toast.LENGTH_SHORT).show()
                            }
                        }) { Text("Cargar base de datos") }
                    }
                    Footer()
                }
            }
        }
    )
}
