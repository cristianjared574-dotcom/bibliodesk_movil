package com.example.bibliodesk_movil.Modelo

import com.example.bibliodesk_movil.Red.RetrofitCliente

class clsPerfilModelo {
    suspend fun obtenerPerfil(idUsuario: Int): dataPerfil {
        return RetrofitCliente.apiService.obtenerPerfil(idUsuario)
    }
}
