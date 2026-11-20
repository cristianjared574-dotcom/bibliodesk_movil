package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsLoginModelo {
    suspend fun iniciarSesion(Matricula: String, clave: String): dataLoginResponse{
        val request = dataLoginRequest(matricula = Matricula, password = clave)
        return RetrofitCliente.apiService.iniciarSesion(request)
    }
}