package com.example.bibliodesk_movil.Modelo

class clsPerfilModelo {
    suspend fun obtenerPerfil(idUsuario: Int): dataPerfilResponse {
        return RetrofitCliente.apiService.obtenerPerfil(idUsuario)
    }
}
