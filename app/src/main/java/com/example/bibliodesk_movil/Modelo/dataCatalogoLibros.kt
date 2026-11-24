package com.example.bibliodesk_movil.Modelo

import android.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCatalogoLibros(
    @SerialName("id_libro") val idLibro: Int,
    @SerialName("id_categoria") val idCategoria: Int,
    val titulo: String,
    val isbn: String? = null,
    val descripcion: String? = null,
    @SerialName("ruta_portada") val rutaPortada: String? = null,
    val autor: String? = null,
    @SerialName("ejemplares_disponibles") val ejemplaresDisponibles: Int,

    val editorial: String?=null,
    @SerialName("anio_publicacion") val anioPublicacion: Int?= null,
    val categoria: String?=null,
    @SerialName("numero_paginas") val numeroPaginas:Int ?= null
)
