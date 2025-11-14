package com.example.panaderia.remote

import com.example.panaderia.model.Producto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("catalogo")
    suspend fun getProductos(): Response<List<Producto>>
}