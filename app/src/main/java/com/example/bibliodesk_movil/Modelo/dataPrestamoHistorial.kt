package com.example.bibliodesk_movil.Modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPrestamoHistorial(
    @SerialName("id_prestamo") val idPrestamo: Int = 0,
    @SerialName("nombre_libro") val nombreLibro: String = "",
    @SerialName("ruta_imagen") val rutaImagen: String = "",
    @SerialName("nombre_autor") val nombreAutor: String = "",
    @SerialName("fecha_prestamo") val fechaPrestamo: String = "",
    @SerialName("fecha_devolucion") val fechaDevolucion: String = "",
    @SerialName("estado") val estado: String = ""
)



