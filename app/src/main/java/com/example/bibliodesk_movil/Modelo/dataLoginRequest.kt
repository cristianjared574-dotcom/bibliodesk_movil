package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class dataLoginRequest(
    val matricula: String,
    val password: String
)
