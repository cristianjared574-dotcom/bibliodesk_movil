package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRegistroRequest(


    @SerialName("matricula")
    val matricula: String,

    @SerialName("nombre")
    val nombre: String,

    @SerialName("apellido_paterno")
    val apellidoPaterno: String,

    @SerialName("apellido_materno")
    val apellidoMaterno: String,

    @SerialName("correo")
    val correo: String,

    @SerialName("telefono")
    val telefono: String,

    @SerialName("contrasena")
    val contrasena: String,

    @SerialName("confirmar_contrasena")
    val confirmarContrasena: String





)
