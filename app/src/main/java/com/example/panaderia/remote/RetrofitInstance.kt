package com.example.panaderia.remote

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {
    // Se instancia el servicio de la API una sola vez
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // URL base de la api, para conectarse desde el emulador al puerto del computador
            .addConverterFactory(GsonConverterFactory.create()) // Conversor de json
            .build()
            .create(ApiService::class.java) // Implementa la interfaz ApiService
    }
}