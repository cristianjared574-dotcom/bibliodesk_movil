package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class dataCerrarSesionResponse(
    val exito: Boolean,
    val mensaje: String
)
