package com.example.panaderia.remote

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitInstcance {
    // Se instancia el servicio de la API una sola vez
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://demo5784683.mockable.io/") // URL base de la api
            .addConverterFactory(GsonConverterFactory.create()) // Conversor de json
            .build()
            .create(ApiService::class.java) // Implementa la interfaz ApiService
    }
}