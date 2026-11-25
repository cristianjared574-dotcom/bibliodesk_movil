package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataLibroDetalle(
    @SerialName("titulo") val titulo: String="",
    @SerialName("autor") val autor: String="",
    @SerialName("categoria")val categoria: String="",
    @SerialName("descripcion")val descripcion: String= "",
    @SerialName("isbn")val isbn: String = "",
    @SerialName("editorial")val editorial: String="",
    @SerialName("anio")val anio: Int =0,
    @SerialName("paginas")val paginas: Int = 0,
    @SerialName("ruta_portada") val ruta_portada: String="",
    @SerialName("ejemplares_disponibles")val ejemplares_disponibles: Int=0
)
