package com.example.bibliodesk_movil.Modelo

class clsCategoriasModelo {
    suspend fun obtenerCategorias(): List<dataCategorias>{
        val datos = RetrofitCliente.apiService.obtenerCategorias()

        val listaFinal = mutableListOf(
            dataCategorias(0, "Todas las categorias")
        )
        listaFinal.addAll(datos)
        return listaFinal
    }
}