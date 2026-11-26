package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsVisorTextoModelo
import com.example.bibliodesk_movil.Modelo.dataContenidoLibro
import com.example.bibliodesk_movil.Vista.VisortextoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsVisorTextoPresentador(private val vista: VisortextoActivity) {

    private val modelo = clsVisorTextoModelo()
    private var secciones: List<dataContenidoLibro> = emptyList()
    private var indiceActual = 0

    fun iniciar(idLibro: Int) {
        if (idLibro <= 0) {
            vista.mostrarError("No se recibió un libro válido")
            return
        }

        android.util.Log.d("VISOR", "Consultando libro: $idLibro")
        cargarContenido(idLibro)
    }

    fun irSeccionAnterior() {
        if (indiceActual > 0) {
            indiceActual--
            mostrarSeccionActual()
        }
    }

    fun irSeccionSiguiente() {
        if (indiceActual < secciones.size - 1) {
            indiceActual++
            mostrarSeccionActual()
        }
    }

    private fun cargarContenido(idLibro: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                android.util.Log.d("VISOR", "Consultando contenido...")

                secciones = modelo.obtenerSecciones(idLibro)
                indiceActual = 0

                android.util.Log.d("VISOR", "Secciones recibidas: ${secciones.size}")

                if (secciones.isNotEmpty()) {
                    mostrarSeccionActual()
                } else {
                    vista.mostrarSinContenido()
                }

            } catch (e: Exception) {
                android.util.Log.e("VISOR", "Error al consultar API", e)
                vista.mostrarError("Error al cargar: ${e.localizedMessage}")
            }
        }
    }

    private fun mostrarSeccionActual() {
        val seccion = secciones[indiceActual]
        vista.mostrarSeccion(
            seccion = seccion,
            posicionActual = indiceActual + 1,
            totalSecciones = secciones.size,
            hayAnterior = indiceActual > 0,
            haySiguiente = indiceActual < secciones.size - 1
        )
    }
}