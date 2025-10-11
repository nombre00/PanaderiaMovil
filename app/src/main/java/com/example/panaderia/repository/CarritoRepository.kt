package com.example.panaderia.repository

import android.content.Context // Para trabajar contexto
import androidx.datastore.core.DataStore  // Para manejar almacenamiento local
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.panaderia.model.Carrito
import com.example.panaderia.model.DesserializarCarritos
import com.example.panaderia.model.SerializarCarrito
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Los repositorios son los encargados de manejar el acceso a los datos.

// Creamos DataStore, esta va a ser nuestra base de datos que va a contener los carritos en formato JSON.
val Context.dataStore2: DataStore<Preferences> by preferencesDataStore(name = "base de datos carritos")

// Clave para almacenar y reconocer la lista.
val CARRITOS_LLAVE = stringPreferencesKey("base de datos carritos")


// Funcion (con corrutinas) para almacenar el carrito en el local storage.
suspend fun guardarCarrito(context: Context, carrito: Carrito){

    // Creamos una variable que guarda los datos de un carrito.
    val json = SerializarCarrito(carrito)

    // Guardamos los datos en el local storage.
    // Editamos el local storage.
    context.dataStore2.edit { preferences ->
        // Le pasamos a la base de datos el json con los datos de los carritos, para eso referimos a la llave.
        preferences[CARRITOS_LLAVE] = json
    }
}



// Funcion que obtiene la lista de carritos del local storage.
// Flow permite leer los datos, esto es usado en el componente.
fun leerCarrtios(context: Context): Flow<List<Carrito>> {
    // Retorna un mapa del contexto.
    return context.dataStore2.data.map { preferences ->
        // Creamos una variable que contiene los datos de la base de datos llamada por su llave
        val json = preferences[CARRITOS_LLAVE] ?: ""
        // Funcion lambda, si la base está vacía retornamos una lista vacía, sino retornamos el json desserializado.
        if (json.isEmpty()) emptyList() else DesserializarCarritos(json)
    }
}