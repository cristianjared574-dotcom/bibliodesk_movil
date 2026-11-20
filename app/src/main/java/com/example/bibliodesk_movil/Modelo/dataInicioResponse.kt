
package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class dataInicioResponse(

    @SerialName("exito")
    val exito: Boolean = false,

    @SerialName("mensaje")
    val mensaje: String? = null,

    @SerialName("usuario")
    val usuario: dataUsuarioInicio? = null,

    @SerialName("resumen")
    val resumen: dataResumenInicio? = null,

    @SerialName("prestamo_actual")
    val prestamoActual: dataPrestamoInicio? = null,

    @SerialName("prestamos")
    val prestamos: List<dataPrestamoInicio> = emptyList()


)

@Serializable
data class dataUsuarioInicio(

    @SerialName("tiene_prestamos")
    val tienePrestamos: Boolean = false,

    @SerialName("total_prestamos")
    val totalPrestamos: Int = 0,

    @SerialName("total_atrasados")
    val totalAtrasados: Int = 0
)

@Serializable
data class dataPrestamoInicio(

    @SerialName("ID_PRESTAMO")
    val idPrestamo: Int = 0,

    @SerialName("FOLIO_PRESTAMO")
    val folioPrestamo: String = "",

    @SerialName("FECHA_INICIO")
    val fechaInicio: String = "",

    @SerialName("FECHA_LIMITE_DEVOLUCION")
    val fechaLimiteDevolucion: String = "",

    @SerialName("ID_LIBRO")
    val idLibro: Int = 0,

    @SerialName("TITULO")
    val titulo: String = "",

    @SerialName("RUTA_PORTADA")
    val rutaPortada: String? = null,

    @SerialName("AUTORES")
    val autores: String = "",

    @SerialName("ESTADO_PRESTAMO")
    val estadoPrestamo: String = ""
)
