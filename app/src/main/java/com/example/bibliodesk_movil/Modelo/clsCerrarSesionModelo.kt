package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsCerrarSesionModelo {
    suspend fun cerrarSesion(): dataCerrarSesion {
        return RetrofitCliente.apiService.cerrarSesion()
    }
}
