
package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class dataInicioResponse(

    @SerialName("exito")
    val exito: Boolean = false,

    @SerialName("mensaje")
    val mensaje: String = "",

    @SerialName("lector")
    val lector: dataLectorInicio? = null,

    @SerialName("prestamo_actual")
    val prestamoActual: dataPrestamoInicio? = null,

    @SerialName("mensaje_prestamos")
    val mensajePrestamos: String? = null
)

@Serializable
data class dataLectorInicio(

    @SerialName("nombre")
    val nombre: String = ""
)

@Serializable
data class dataPrestamoInicio(

    @SerialName("id_prestamo")
    val idPrestamo: Int = 0,

    @SerialName("titulo")
    val titulo: String = "",

    @SerialName("autor")
    val autor: String = "",

    @SerialName("ruta_portada")
    val rutaPortada: String? = null,

    @SerialName("fecha_inicio")
    val fechaInicio: String = ""
)