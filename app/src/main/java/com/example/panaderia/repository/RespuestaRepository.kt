package com.example.panaderia.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.panaderia.model.Respuesta
import com.example.panaderia.model.serializarRespuesta
import com.example.panaderia.model.desserializarRespuesta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore4: DataStore<Preferences> by preferencesDataStore(name = "respuesta")

// Clave para almacenar y reconocer la lista.
val RESPUESTA_LLAVE = stringPreferencesKey("respuesta")

// Funcion (con corrutinas) para almacenar el carrito en el local storage.
suspend fun guardarRespuesta(context: Context, respuesta: Respuesta){

    // Creamos un json que guarda los datos de los carritos.
    val json = serializarRespuesta(respuesta)

    // Guardamos los datos en el local storage.
    // Editamos el local storage.
    context.dataStore4.edit { preferences ->
        // Le pasamos a la base de datos el json con los datos de los carritos, para eso referimos a la llave.
        preferences[RESPUESTA_LLAVE] = json
    }
}

// Funcion que obtiene la lista de carritos del local storage.
// Flow permite leer los datos, esto es usado en el componente.
fun leerRespuesta(context: Context): Flow<Respuesta> {
    // Retorna un mapa del contexto.
    return context.dataStore4.data.map { preferences ->
        // Creamos un json que contiene los datos de la base de datos llamada por su llave
        val json = preferences[RESPUESTA_LLAVE] ?: ""
        // Funcion lambda, si la base está vacía retornamos una lista vacía, sino retornamos el json desserializado.
        (if (json.isEmpty()) Respuesta(false) else desserializarRespuesta(json))
    }
}