package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPerfil(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("id_usuario") val idUsuario: Int = 0,
    @SerialName("nombre_completo") val nombre_completo: String = "",
    @SerialName("matricula") val matricula: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("telefono") val telefono: String = "",
    @SerialName("tipo_usuario") val tipo_usuario: String = ""
)
