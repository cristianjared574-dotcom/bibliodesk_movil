package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsInicioModelo
import com.example.bibliodesk_movil.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsInicioPresentador(private val view: MainActivity) {

    private val modelo = clsInicioModelo()

    fun obtenerInicio(idUsuario: Int) {

        // validar el identificador del usuario
        if (idUsuario <= 0) {
            view.mostrarError("Usuario no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val respuesta = withContext(Dispatchers.IO) {
                    modelo.obtenerInicio(idUsuario)
                }

                if (respuesta.exito) {
                    view.mostrarInicio(respuesta) //porque envia datos del lector el resumenes y prestamos
                } else {
                    view.mostrarError(respuesta.mensaje)
                }

            } catch (e: Exception) {
                view.mostrarError(
                    "Error de conexión: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }
}