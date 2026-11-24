package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.Serializable

@Serializable
class dataLibroDetalle {
    val titulo: String=""
    val autor: String=""
    val categoria: String=""
    val descripcion: String?= null
    val isbn: String?= null
    val editorial: String=""
    val anio: Int? =null
    val paginas: Int? = null
    val ruta_portada: String?= null
    val ejemplares_disponibles: Int=0
}
