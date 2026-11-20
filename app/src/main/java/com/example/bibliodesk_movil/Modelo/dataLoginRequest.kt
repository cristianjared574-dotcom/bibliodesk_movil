package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataLoginRequest(
    @SerialName("matricula") val matricula: String = "",
    @SerialName("password") val password: String = ""
)
