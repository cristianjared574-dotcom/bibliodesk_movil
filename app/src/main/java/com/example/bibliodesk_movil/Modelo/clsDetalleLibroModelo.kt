package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsDetalleLibroModelo {
    suspend fun obtenerDetalleLibro(idLibro: Int): dataLibroDetalleResponse {
        return RetrofitCliente.apiService.obtenerDetalleLibro(idLibro)
    }
}