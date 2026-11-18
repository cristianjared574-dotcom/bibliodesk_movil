package com.example.bibliodesk_movil.Modelo

class clsCerrarSesionModelo {
    suspend fun cerrarSesion(): dataCerrarSesionResponse {
        return RetrofitCliente.apiService.cerrarSesion()
    }
}
