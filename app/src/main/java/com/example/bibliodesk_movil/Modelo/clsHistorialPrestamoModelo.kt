package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsHistorialPrestamoModelo {
    suspend fun obtenerHistorial(idUsuario: Int): List<dataPrestamoHistorial> {
        return RetrofitCliente.apiService.obtenerHistorialPrestamos(idUsuario)
    }

    fun obtenerOpcionesFiltro(lista: List<dataPrestamoHistorial>): List<String> {
        val estadosDinamicos = lista.map { it.estado.trim() }.filter { it.isNotEmpty() }.distinct()
        return listOf("Todos") + estadosDinamicos
    }

    fun filtrarPorEstado(lista: List<dataPrestamoHistorial>, estadoSeleccionado: String): List<dataPrestamoHistorial> {
        if (estadoSeleccionado.isBlank() || estadoSeleccionado.equals("Todos", ignoreCase = true)) {
            return lista
        }
        return lista.filter { it.estado.equals(estadoSeleccionado, ignoreCase = true) }
    }
}