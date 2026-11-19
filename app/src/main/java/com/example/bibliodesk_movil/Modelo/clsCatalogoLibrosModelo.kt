package com.example.bibliodesk.modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente
import com.example.bibliodesk_movil.Modelo.dataCatalogoLibros
import com.example.bibliodesk_movil.Modelo.dataLibroDetalleResponse


class clsCatalogoLibrosModelo {
    suspend fun obtenerLibros(idCategoria: Int): List<dataCatalogoLibros>{
        return RetrofitCliente.apiService.obtenerLibros(idCategoria)
    }

    suspend fun obtenerDetalleLibro(idLibro: Int): dataLibroDetalleResponse {
        return RetrofitCliente.apiService.obtenerDetalleLibro(idLibro)
    }
}
