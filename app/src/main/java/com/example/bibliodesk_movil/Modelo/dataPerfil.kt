package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPerfil(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("datos") val datos: dataLector? = null,
    @SerialName("id_usuario") val idUsuario: Int = 0
)
