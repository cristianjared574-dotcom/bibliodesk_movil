package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente
import retrofit2.Response

class clsRegistroModelo {

    suspend fun registrarUsuario( request: dataRegistroRequest): Response<dataRegistroResponse> {
        return RetrofitCliente.apiService.registrarUsuario(request)
    }
}