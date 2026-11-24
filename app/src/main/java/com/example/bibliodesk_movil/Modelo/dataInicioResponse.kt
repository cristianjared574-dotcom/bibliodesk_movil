package com.example.bibliodesk_movil.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class dataInicioResponse(
    val exito: Boolean = false,
    val mensaje: String = "",

    val usuario: dataUsuarioInicio? = null,
    val resumen: dataResumenInicio? = null,

    @SerialName("prestamo_actual")
    val prestamoActual: dataPrestamoInicio? = null,

    val prestamos: List<dataPrestamoInicio> = emptyList()   //varios prestamos en una lista que emepieza vacia
)

// datos del lector
@Serializable
data class dataUsuarioInicio(
    @SerialName("id_usuario")
    val idUsuario: Int = 0,

    val matricula: String = "",
    val nombre: String = "",

    @SerialName("apellido_paterno")
    val apellidoPaterno: String = "",

    @SerialName("apellido_materno")
    val apellidoMaterno: String? = null,

    val correo: String = "",
    val telefono: String? = null
)

// resumen de préstamos
@Serializable
data class dataResumenInicio(
    @SerialName("tiene_prestamos")
    val tienePrestamos: Boolean = false,

    @SerialName("total_prestamos")
    val totalPrestamos: Int = 0,

    @SerialName("total_atrasados")
    val totalAtrasados: Int = 0
)

// informcion de cada prestamo
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

    @SerialName("ID_EJEMPLAR")
    val idEjemplar: Int = 0,

    @SerialName("CODIGO_EJEMPLAR")
    val codigoEjemplar: String = "",

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
