package com.example.panaderia.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.panaderia.model.Cliente
import com.example.panaderia.model.DesserializarCliente
import com.example.panaderia.model.DesserializarClienteIngresado
import com.example.panaderia.model.SerializarCliente
import com.example.panaderia.model.SerializarClienteIngresado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Los repositorios son los encargados de manejar el acceso a los datos.

// Creamos DataStore, esta va a ser nuestra base de datos que va a contener los clientes en formato JSON.
val Context.dataStore5: DataStore<Preferences> by preferencesDataStore(name = "base de datos clientes")

// Para almacenar y reconocer la lista
val CLIENTES_LLAVE = stringPreferencesKey("base de datos clientes")


// Creamos un dataStore para el cliente ingresado, solo va a guardar un Cliente.
val Context.dataStore6: DataStore<Preferences> by preferencesDataStore(name = "usuario ingresado")
// LLave
val INGRESADO_LLAVE = stringPreferencesKey("usuario ingresado")


// Funcion con corrutinas para almacenar los clientes
suspend fun guardarClientes(contexto: Context,clientes: List<Cliente>){
    // Creamos un jeson que vamos a guardar.
    val json = SerializarCliente(clientes)
    // Guarda los datos en el local storage
    contexto.dataStore5.edit { preferences ->
        // Usamos la llave para guardar
        preferences[CLIENTES_LLAVE] = json
    }
}

// Funcion que obtiene la lista de envios del local storage.
// Flow permite leer los datos, esto es usado en el componente.
fun leerClientes(contexto: Context): Flow<List<Cliente>> {
    return contexto.dataStore5.data.map { preferences ->
        // Creamos un json que contiene los datos de la base de datos llamada por su llave.
        val json = preferences[CLIENTES_LLAVE] ?: ""
        // Funcion lambda, si la base está vacía retornamos una lista vacía, sino retornamos el json desserializado.
        if (json.isEmpty()) emptyList() else DesserializarCliente(json)
    }
}


// Funcion que guarda el cliente ingresado.
suspend fun guardarClienteIngresado(contexto: Context, cliente: Cliente){
    val json = SerializarClienteIngresado(cliente)
    contexto.dataStore6.edit { preferences ->
        preferences[INGRESADO_LLAVE] = json
    }
}
// Funcion que obtiene el cliente ingresado del local storage
fun leerClienteIngresado(contexto: Context): Flow<Cliente> {
    return contexto.dataStore6.data.map { preferences ->
        val json = preferences[INGRESADO_LLAVE] ?: ""
        if (json.isEmpty()) Cliente("0","","","","", "",emptyList()) else DesserializarClienteIngresado(json)
    }
}










