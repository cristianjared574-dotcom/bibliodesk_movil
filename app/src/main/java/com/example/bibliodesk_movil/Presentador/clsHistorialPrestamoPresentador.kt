package com.example.bibliodesk_movil.Presentador

import com.example.bibliodesk_movil.Modelo.clsHistorialPrestamoModelo
import com.example.bibliodesk_movil.Modelo.dataPrestamoHistorial
import com.example.bibliodesk_movil.Vista.HistorialprestamoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsHistorialPrestamoPresentador(private val vista: HistorialprestamoActivity) {

    private val modelo = clsHistorialPrestamoModelo()
    private var listaHistorial: List<dataPrestamoHistorial> = emptyList()
    private var listaEstados: List<String> = listOf("Todos")

    fun iniciar(idUsuario: Int) {
        if (idUsuario > 0) {
            cargarHistorial(idUsuario)
        } else {
            vista.mostrarError("No se recibió un ID de usuario válido")
        }
    }

    fun seleccionarFiltro(posicion: Int) {
        if (posicion >= 0 && posicion < listaEstados.size) {
            val estadoSeleccionado = listaEstados[posicion]
            val filtrada = modelo.filtrarPorEstado(listaHistorial, estadoSeleccionado)
            vista.mostrarHistorial(filtrada)
        }
    }

    private fun cargarHistorial(idUsuario: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaHistorial = modelo.obtenerHistorial(idUsuario)
                listaEstados = modelo.obtenerOpcionesFiltro(listaHistorial)
                vista.mostrarEstados(listaEstados)
                vista.mostrarHistorial(listaHistorial)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar el historial: " + e.localizedMessage)
            }
        }
    }
}