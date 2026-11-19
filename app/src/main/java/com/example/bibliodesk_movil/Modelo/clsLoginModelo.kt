package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsLoginModelo {
    suspend fun iniciarSesion(matricula: String, password: String): dataLoginResponse{
        val request = dataLoginRequest(matricula, password)
        return RetrofitCliente.apiService.iniciarSesion(request)
    }
}