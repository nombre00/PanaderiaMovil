package com.example.panaderia.remote

import com.example.panaderia.model.Carrito
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.Envio
import com.example.panaderia.model.Producto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // Get varios
    @GET("catalogo")
    suspend fun getProductos(): Response<List<Producto>>
    @GET("carritos")
    suspend fun getCarritos(): Response<List<Carrito>>
    @GET("clientes")
    suspend fun getClientes(): Response<List<Cliente>>
    @GET("envios")
    suspend fun getEnvios(): Response<List<Envio>>


    // Get por id
    @GET("catalogo/{id}")
    suspend fun getProductoPorId(id: Int): Response<Producto>
    @GET("carritos/{id}")
    suspend fun getCarritoPorId(id: Int): Response<Carrito>
    @GET("clientes/{id}")
    suspend fun getClientePorId(id: Int): Response<Cliente>
    @GET("envios/{id}")
    suspend fun getEnvioPorId(id: Int): Response<Envio>


    // Guardar
    @POST("catalogo/{id}")
    suspend fun guardarProducto(@Path("id") id: Int, @Body producto: Producto): Response<Producto>
    @POST("carritos/{id}")
    suspend fun guardarCarrito(@Path("id") id: Int, @Body carrito: Carrito): Response<Carrito>
    @POST("clientes/{id}")
    suspend fun guardarCliente(@Path("id") id: Int, @Body cliente: Cliente): Response<Cliente>
    @POST("envios/{id}")
    suspend fun guardarEnvio(@Path("id") id: Int, @Body envio: Envio): Response<Envio>


    // Actualizar
    @PUT("catalogo/{id}")
    suspend fun actualizarProducto(@Path("id") id: Int, @Body producto: Producto): Response<Producto>
    @PUT("carritos/{id}")
    suspend fun actualizarCarrito(@Path("id") id: Int, @Body carrito: Carrito): Response<Carrito>
    @PUT("clientes/{id}")
    suspend fun actualizarCliente(@Path("id") id: Int, @Body cliente: Cliente): Response<Cliente>
    @PUT("envios/{id}")
    suspend fun actualizarEnvio(@Path("id") id: Int, @Body envio: Envio): Response<Envio>


    // Borrar
    @DELETE("catalogo/{id}")
    suspend fun borrarProducto(@Path("id") id: Int): Response<Unit>
    @DELETE("carritos/{id}")
    suspend fun borrarCarrito(@Path("id") id: Int): Response<Unit>
    @DELETE("clientes/{id}")
    suspend fun borrarCliente(@Path("id") id: Int): Response<Unit>
    @DELETE("envios/{id}")
    suspend fun borrarEnvio(@Path("id") id: Int): Response<Unit>
}