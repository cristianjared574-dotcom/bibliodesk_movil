package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class dataPerfilResponse(
    val exito: Boolean,
    val datos: dataPerfil? = null,
    val mensaje: String? = null
)

@Serializable
data class dataPerfil(
    val nombre_completo: String,
    val matricula: String,
    val correo: String,
    val telefono: String? = null,
    val tipo_usuario: String
)
