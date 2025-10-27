package com.example.panaderia.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.panaderia.R

class Notificationes(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Definimos identificadores únicos para el canal y la notificación.
    companion object {
        private const val CHANNEL_ID = "pedidos_channel"
        private const val CHANNEL_NAME = "Notificaciones de Pedidos"
        private const val NOTIFICATION_ID = 1
    }
    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH // IMPORTANCE_HIGH hace que la notificación aparezca como "heads-up".
            ).apply {
                description = "Canal para notificar el estado de los pedidos."
                // Aquí podrías configurar más cosas como la vibración, el led, etc.
            }
            // Registramos el canal en el sistema.
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun mostrarNotificacion(titulo: String, contenido: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titulo)
            .setContentText(contenido)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioridad alta para que aparezca en la pantalla.
            .setAutoCancel(true) // La notificación se cierra automaticamente cuando el usuario la toca.

        // Mostramos la notificacion.
        // El ID nos permite actualizar o cancelar esta notificación más tarde si es necesario.
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}