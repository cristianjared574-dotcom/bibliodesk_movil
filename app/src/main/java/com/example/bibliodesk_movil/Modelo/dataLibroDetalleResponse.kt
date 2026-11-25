package com.example.bibliodesk_movil.Modelo


import kotlinx.serialization.Serializable
@Serializable
data class dataLibroDetalleResponse(
    val exito: Boolean,
    val datos: dataLibroDetalle?= null,
    val mensaje: String?= null
)
