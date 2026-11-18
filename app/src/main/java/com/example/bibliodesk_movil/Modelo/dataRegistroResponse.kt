package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRegistroResponse(


    @SerialName("exito")
    val exito: Boolean = false,

    @SerialName("mensaje")
    val mensaje: String = "",

    @SerialName("id_usuario")
    val idUsuario: Int? = null

)
