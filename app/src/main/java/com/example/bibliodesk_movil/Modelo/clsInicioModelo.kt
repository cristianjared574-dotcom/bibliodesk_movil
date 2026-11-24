package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsInicioModelo {
    suspend fun obtenerInicio(idUsuario: Int): dataInicioResponse {
        return RetrofitCliente.apiService.obtenerInicio(idUsuario)
    }
}