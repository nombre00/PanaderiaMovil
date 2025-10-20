package com.example.panaderia.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.panaderia.model.DesserializarEnvios
import com.example.panaderia.model.Envio
import com.example.panaderia.model.SerializarEnvio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Los repositorios son los encargados de manejar el acceso a los datos.

// Creamos DataStore, esta va a ser nuestra base de datos que va a contener los carritos en formato JSON.
val Context.dataStore3: DataStore<Preferences> by preferencesDataStore(name = "base de datos envios")

// LLave para almacenar y reconocer la lista.
val ENVIOS_LLAVE = stringPreferencesKey("base de datos envios")


// Funcion con corrutinas para almacenar los envios.
suspend fun guardarEnvio(contexto: Context, envios: List<Envio>){
    // Creamos un json que guarda los carritos.
    val json = SerializarEnvio(envios)
    // Guarda los datos en el local storage.
    contexto.dataStore3.edit { preferences ->
        // Le pasamos a la base de datos el json con los datos de los carritos, para eso referimos a la llave.
        preferences[ENVIOS_LLAVE] = json
    }
}


// Funcion que obtiene la lista de envios del local storage.
// Flow permite leer los datos, esto es usado en el componente.
fun leerEnvios(contexto: Context): Flow<List<Envio>> {
    // Retorna un mapa del contexto.
    return contexto.dataStore3.data.map { preferences -> // creo que mapear es iterar
        // Creamos un json que contiene los datos de la base de datos llamada por su llave.
        val json = preferences[ENVIOS_LLAVE] ?: ""
        // Funcion lambda, si la base está vacía retornamos una lista vacía, sino retornamos el json desserializado.
        if (json.isEmpty()) emptyList() else DesserializarEnvios(json)
    }
}