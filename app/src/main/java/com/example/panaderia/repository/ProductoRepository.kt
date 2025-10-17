package com.example.panaderia.repository

import android.content.Context // Para trabajar contexto
import androidx.datastore.core.DataStore  // Para manejar almacenamiento local
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.panaderia.model.Producto
import com.example.panaderia.model.deserializarProductos
import com.example.panaderia.model.serializarProductos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map




// Los repositorios son los encargados de manejar el acceso a los datos.




// Creamos DataStore, esta va a ser nuestra base de datos que va a contener el catalogo en formato JSON.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Base de datos catalogo")

// Clave para almacenar y reconocer la lista.
val CATALOGO_PRODUCTOS_LLAVE = stringPreferencesKey("Base de datos catalogo")


// Funcion (con corrutinas) para almacenar la lista en local storage.
suspend fun guardarCatalogo(context: Context, productos: List<Producto>){

    // Creamos una variable que guarda los datos de los productos del catálogo.
    val json = serializarProductos(productos)

    // Guardamos los datos en el local storage.
    // Editamos el local storage.
    context.dataStore.edit { preferences ->
        // Le pasamos a la base de datos el json con los datos del catalogo, para eso referimos a la llave.
        preferences[CATALOGO_PRODUCTOS_LLAVE] = json
    }
}



// Funcion que obtiene la lista de productos del local storage.
// Flow permite leer los datos, esto es usado en el componente, voy a intentar hacer todo en inicio y luego solo referenciar local storage.
fun leerCatalogoLS(context: Context): Flow<List<Producto>> {
    // Retorna un mapa del contexto...
    return context.dataStore.data.map { preferences ->
        // Creamos una variable que contiene los datos de la base de datos llamada por su llave
        val json = preferences[CATALOGO_PRODUCTOS_LLAVE] ?: ""
        // Funcion lambda, si la base está vacía retornamos una lista vacía, sino retornamos el json desserializado.
        if (json.isEmpty()) emptyList() else deserializarProductos(json)
    }
}