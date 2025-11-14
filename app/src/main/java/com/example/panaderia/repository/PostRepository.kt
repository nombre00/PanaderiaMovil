package com.example.panaderia.repository

import com.example.panaderia.model.Producto
import com.example.panaderia.remote.RetrofitInstcance
import retrofit2.Response

// Este repositorio accede a los datos usando retrofit

class PostRepository {
    // Funcion que obtiene los post desde la api.
    suspend fun getPost(): Response<List<Producto>> {
        return RetrofitInstcance.api.getProductos()
    }
}