package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsDetalleLibroModelo
import com.example.bibliodesk_movil.Vista.DetalleLibroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleLibroPresentador(private val view: DetalleLibroActivity) {

    private val modelo = clsDetalleLibroModelo()

    fun cargarDetalle(idLibro: Int) {
        if (idLibro <= 0) {
            view.mostrarError("ID de libro inválido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.obtenerDetalleLibro(idLibro)

                if (respuesta.exito && respuesta.datos != null) {
                    view.mostrarDetalleLibro(respuesta.datos)
                } else {
                    view.mostrarError(respuesta.mensaje ?: "No se pudo cargar el detalle del libro")
                }
            } catch (e: Exception) {
                view.mostrarError("Error: " + e.localizedMessage)
            }
        }
    }
}