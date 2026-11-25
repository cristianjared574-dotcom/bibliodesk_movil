package com.example.bibliodesk_movil.Modelo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class dataLibroDetalleResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("id_libro") val idLibro: Int = 0,
    @SerialName("datos") val datos: dataLibroDetalle?= null,
)
