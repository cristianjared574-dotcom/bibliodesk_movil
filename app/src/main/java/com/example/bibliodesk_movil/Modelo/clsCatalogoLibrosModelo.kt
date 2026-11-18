package com.example.bibliodesk_movil.Modelo

class clsCatalogoLibrosModelo {
    suspend fun obtenerLibros(idCategoria: Int): List<dataCatalogoLibros> {
        return RetrofitCliente.apiService.obtenerLibros(idCategoria)
    }

}