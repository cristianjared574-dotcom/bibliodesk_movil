package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCategorias(
    @SerialName("id_categoria") val idCategori: Int=0,
    @SerialName("nombre_categoria") val nombreCategoria: String=""
)
{
    override fun toString(): String{
        return nombreCategoria
    }
}