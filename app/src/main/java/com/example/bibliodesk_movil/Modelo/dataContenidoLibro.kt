package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataContenidoLibro(
    @SerialName("id_contenido") val idContenido: Int = 0,
    @SerialName("id_libro") val idLibro: Int = 0,
    @SerialName("titulo_libro") val tituloLibro: String = "",
    @SerialName("orden") val orden: Int = 0,
    @SerialName("titulo_seccion") val tituloSeccion: String = "",
    @SerialName("contenido") val contenido: String = ""
)
