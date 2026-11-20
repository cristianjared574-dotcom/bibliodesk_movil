package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente
import retrofit2.Response

class clsInicioModelo {

    suspend fun obtenerInicio(idUsuario: Int): Response<dataInicioResponse> {
        return RetrofitCliente.apiService.obtenerInicio(idUsuario)
    }
}