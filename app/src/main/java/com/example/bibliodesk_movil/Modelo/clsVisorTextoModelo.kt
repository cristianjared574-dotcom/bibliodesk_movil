package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsVisorTextoModelo {
    suspend fun obtenerSecciones(idLibro: Int): List<dataContenidoLibro> {
        return RetrofitCliente.apiService.obtenerContenidoLibro(idLibro)
    }
}