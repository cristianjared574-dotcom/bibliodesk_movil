package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataLoginResponse(
    val exito: Boolean,
    val mensaje: String,
    @SerialName("id_usuario") val idUsuario: Int? = null,
    val matricula: String? = null,
    val nombre: String? = null
)
